package controller;

import model.classes.*;
import model.enums.ClassType;
import model.enums.VictualTransactionStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

public class TransactionController {
    public int checkSeatAvailability(int scheduleID, String classType) {
        System.out.println("Schedule ID: " + scheduleID);
        System.out.println("Class Type: " + classType);

        ConnectionHandler.getInstance().connect();

        String query = "SELECT schedule_capacity.occupied, carriage.capacity FROM carriage JOIN schedule_capacity ON carriage.carriage_id = schedule_capacity.carriage_id WHERE schedule_capacity.schedule_id = ? AND carriage.class = ? AND carriage.type = 'SEATING'";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, scheduleID);
            stmt.setString(2, classType);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("capacity") - rs.getInt("occupied");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return -1;
    }

    public boolean claimVictual(int transactionID) {
        ConnectionHandler.getInstance().connect();

        String query = "UPDATE victuals_transaction SET status = 'CLAIMED' WHERE transaction_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, transactionID);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }

    public String getTicketBuyerEmail(int transactionID) {
        ConnectionHandler.getInstance().connect();

        String query = "SELECT passenger.email FROM passenger JOIN ticket_transaction ON passenger.user_id = ticket_transaction.user_id WHERE ticket_transaction.transaction_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, transactionID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return null;
    }

    public TicketTransaction getTicketTransaction(int transactionID) {
        ConnectionHandler.getInstance().connect();

        String query = "SELECT * FROM ticket_transaction WHERE transaction_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, transactionID);
            ResultSet rs = stmt.executeQuery();

            TicketTransaction transaction = new TicketTransaction();

            while (rs.next()) {
                transaction.setTransactionID(rs.getInt("transaction_id"));
                transaction.setScheduleID(rs.getInt("schedule_id"));
                transaction.setDatePurchase(rs.getTimestamp("purchase_date"));
                transaction.setPassengers(rs.getInt("passengers"));
                transaction.setCommute(rs.getBoolean("commute"));
                transaction.setTotal(rs.getDouble("total"));
                transaction.setType(ClassType.valueOf(rs.getString("type")));
            }

            return transaction;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
    }

    public boolean bookTicket(int userID, int scheduleID, int passengers, boolean commute, double total, String type) {
        int stationID = getStationIDByScheduleID(scheduleID);
        StationController stationController = new StationController();

        if (!deductBalance(userID, total) || !stationController.addStationIncome(stationID, total)) {
            return false;
        }

        ConnectionHandler.getInstance().connect();

        String query = "INSERT INTO ticket_transaction(user_id, schedule_id, passengers, type, commute, total) VALUES(?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, userID);
            stmt.setInt(2, scheduleID);
            stmt.setInt(3, passengers);
            stmt.setString(4, type);
            stmt.setBoolean(5, commute);
            stmt.setDouble(6, total);
            stmt.executeUpdate();

            LoyaltyController controller = new LoyaltyController();
            controller.addTotalPaid(userID, total);
            controller.updateLoyalty(userID);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }

    private int getStationIDByScheduleID(int scheduleID) {
        ConnectionHandler.getInstance().connect();
        String query = "SELECT departure_station_id FROM schedule WHERE schedule_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, scheduleID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("departure_station_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return -1;
    }

    public boolean updateOccupied(int carriage, int schedule_id, int passenger) {
        String query = "SELECT carriage.capacity, carriage.carriage_id, schedule_capacity.occupied FROM schedule_capacity JOIN carriage ON carriage.carriage_id = schedule_capacity.carriage_id WHERE schedule_capacity.schedule_id = ? AND schedule_capacity.carriage_id = ?";
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, schedule_id);
            stmt.setInt(2, carriage);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (carriage == rs.getInt("carriage_id")) {
                    int occupied = rs.getInt("occupied");
                    if (occupied + passenger < rs.getInt("capacity")) {
                        String updateOccupied = "UPDATE schedule_capacity SET occupied = occupied + ? WHERE schedule_id = ? AND carriage_id = ?";

                        try {
                            PreparedStatement stmt2 = ConnectionHandler.getInstance().con.prepareStatement(updateOccupied);
                            stmt2.setInt(1, occupied);
                            stmt2.setInt(2, schedule_id);
                            stmt2.setInt(3, carriage);
                            stmt2.executeUpdate();
                        } catch (SQLException e) {
                            return false;
                        }
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return false;
    }

    public ArrayList<Transaction> getTicketTransactionList(int userID) {
        ConnectionHandler.getInstance().connect();

        ArrayList<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT * FROM ticket_transaction WHERE user_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TicketTransaction transaction = new TicketTransaction();
                transaction.setTransactionID(rs.getInt("transaction_id"));
                transaction.setScheduleID(rs.getInt("schedule_id"));
                transaction.setDatePurchase(rs.getTimestamp("purchase_date"));
                transaction.setPassengers(rs.getInt("passengers"));
                transaction.setType(ClassType.valueOf(rs.getString("type")));
                transaction.setCommute(rs.getBoolean("commute"));
                transaction.setRescheduled(rs.getBoolean("rescheduled"));
                transaction.setTotal(rs.getDouble("total"));
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return transactionList;
    }

    public ArrayList<Transaction> getVictualTransactionList(int userID) {
        ConnectionHandler.getInstance().connect();

        ArrayList<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT vt.transaction_id, vt.user_id, vt.station_id, vt.date, vt.total, vt.status, ti.victual_id, ti.quantity FROM victuals_transaction vt LEFT JOIN transaction_item ti ON vt.transaction_id = ti.transaction_id WHERE vt.user_id = ? ORDER BY vt.date DESC";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VictualTransaction transaction = new VictualTransaction();
                transaction.setTransactionID(rs.getInt("transaction_id"));
                transaction.setStationID(rs.getInt("station_id"));
                transaction.setDatePurchase(rs.getTimestamp("date"));
                transaction.setTotal(rs.getDouble("total"));
                transaction.setStatus(VictualTransactionStatus.valueOf(rs.getString("status")));

                HashMap<Integer, Integer> victualBought = new HashMap<>();

                do {
                    victualBought.put(rs.getInt("victual_id"), rs.getInt("quantity"));
                } while (rs.next() && rs.getInt("transaction_id") == transaction.getTransactionID());

                if (!rs.isBeforeFirst()) {
                    rs.previous();
                }

                transaction.setItems(victualBought);
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return transactionList;
    }

    public boolean cancelVictualTransaction(VictualTransaction transaction, int userId, String email) {
        if (refundVictualTrxBalance(transaction, userId) && returnStock(transaction.getItems(), transaction.getStationID()) && deleteTransactionItems(transaction.getTransactionID())) {
            ConnectionHandler.getInstance().connect();

            String query = "DELETE FROM victuals_transaction WHERE transaction_id = ?";

            try {
                PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
                stmt.setInt(1, transaction.getTransactionID());
                stmt.executeUpdate();

                NotificationController notificationController = new NotificationController();
                notificationController.sendNotification(email, "Victuals Cancelation", "Hi ---, we have successfully canceled your victual transaction and returned Rp " + transaction.getTotal() + " to your wallet.\n\nThank you!");
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                ConnectionHandler.getInstance().disconnect();
            }

            return true;
        }

        return false;
    }

    public boolean allowReschedule(int scheduleID) {
        ConnectionHandler.getInstance().connect();
        String query = "SELECT departure_date FROM schedule WHERE schedule_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, scheduleID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Timestamp departureTimestamp = rs.getTimestamp("departure_date");
                LocalDate departureDate = departureTimestamp.toLocalDateTime().toLocalDate();
                LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());

                if (currentDate.isBefore(departureDate)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return false;
    }

    public void updateVictualTransactionStatus(int userID) {
        ConnectionHandler.getInstance().connect();
        String query = "UPDATE victuals_transaction SET status = 'EXPIRED' WHERE user_id = ? AND status = 'PENDING' AND date <= NOW() - INTERVAL 7 DAY";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, userID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
    }

    public boolean removeTransactionItem(int victualID) {
        ConnectionHandler.getInstance().connect();

        String query = "UPDATE transaction_item SET victual_id = NULL WHERE victual_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, victualID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }

    private boolean returnStock(HashMap<Integer, Integer> victuals, int stationId) {
        StockController controller = new StockController();

        for (Integer victualId : victuals.keySet()) {
            controller.addStock(victualId, stationId, victuals.get(victualId));
        }

        return true;
    }

    private boolean deleteTransactionItems(int transactionID) {
        ConnectionHandler.getInstance().connect();

        String query = "DELETE FROM transaction_item WHERE transaction_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, transactionID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean deductBalance(int userID, double amount) {
        WalletController controller = new WalletController();

        if (controller.getWallet(userID).getBalance() < amount) {
            return false;
        }

        ConnectionHandler.getInstance().connect();
        String query = "UPDATE wallet SET balance = balance - ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setDouble(1, amount);
            stmt.setInt(2, userID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }

    private boolean refundVictualTrxBalance(VictualTransaction transaction, int userId) {
        ConnectionHandler.getInstance().connect();

        String query = "UPDATE wallet SET balance = balance + ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setDouble(1, transaction.getTotal());
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }
}
