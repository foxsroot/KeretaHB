package controller;

import model.classes.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TransactionController {
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
                transaction.setSchdeuleID(rs.getInt("schedule_id"));
                transaction.setDatePurchase(rs.getTimestamp("purchase_date"));
                transaction.setPassengers(rs.getInt("passengers"));
                transaction.setCommute(rs.getBoolean("commute"));
                transaction.setTotal(rs.getDouble("total"));
            }

            return transaction;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
    }

    public ArrayList<TicketTransaction> getTicketTransactionList(int userID) {
        ConnectionHandler.getInstance().connect();
        ArrayList<TicketTransaction> ticketTransactionList = new ArrayList<>();

        String query = "SELECT * FROM ticket_transaction WHERE userID = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TicketTransaction transaction = new TicketTransaction(rs.getInt("transaction_id"), rs.getTimestamp("purchase_date"), rs.getInt("passengers"), rs.getBoolean("commute"), rs.getInt("schedule_id"), rs.getBoolean("rescheduled"), rs.getDouble("total"));
                ticketTransactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return ticketTransactionList;
    }


    public boolean bookTicket(int userID, int scheduleID, int passengers, boolean commute, double total, String type) {
        if (!deductBalance(userID, total)) {
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

    public boolean updateOccupied(String type, int train_id, int passenger) {
        String query = "SELECT * FROM carriage WHERE train_id = '" + train_id + "'";
        try {
            ConnectionHandler.getInstance().connect();
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("type").equals(type)) {
                    int occupied = rs.getInt("occupied");
                    if (occupied < rs.getInt("capacity")) {
                        rs.updateInt("occupied", occupied + passenger);
                        rs.updateRow();
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

    public ArrayList<Transaction> getVictualTransactionList(int userID) {
        ConnectionHandler.getInstance().connect();

        ArrayList<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT vt.transaction_id, vt.user_id, vt.station_id, vt.date, vt.total, ti.victual_id, ti.quantity FROM victuals_transaction vt LEFT JOIN transaction_item ti ON vt.transaction_id = ti.transaction_id WHERE vt.user_id = ? ORDER BY vt.date DESC";

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
