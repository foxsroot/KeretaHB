package controller;

import model.classes.TicketTransaction;
import model.classes.Transaction;
import model.classes.VictualTransaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TransactionController {
    ConnectionHandler conn = new ConnectionHandler();

    public ArrayList<TicketTransaction> getTicketTransactionList(int userID) {
        conn.connect();
        ArrayList<TicketTransaction> ticketTransactionList = new ArrayList<>();

        String query = "SELECT * FROM ticket_transaction WHERE userID = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TicketTransaction transaction = new TicketTransaction(rs.getInt("transaction_id"), rs.getTimestamp("purchase_date"), rs.getInt("passengers"), rs.getBoolean("commute"), rs.getInt("schedule_id"), rs.getBoolean("rescheduled"), rs.getDouble("total"));
                ticketTransactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return ticketTransactionList;
    }



    public boolean bookTicket(int userID, int scheduleID, int passengers, boolean commute) {
        conn.connect();

        String query = "INSERT INTO ticket_transaction(user_id, schedule_id, passengers, commute) VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, userID);
            stmt.setInt(2, scheduleID);
            stmt.setInt(3, passengers);
            stmt.setBoolean(4, commute);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }

    public ArrayList<Transaction> getVictualTransactionList(int userID) {
        conn.connect();

        ArrayList<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT vt.transaction_id, vt.user_id, vt.station_id, vt.date, vt.total, ti.victual_id, ti.quantity FROM victuals_transaction vt LEFT JOIN transaction_item ti ON vt.transaction_id = ti.transaction_id WHERE vt.user_id = ? ORDER BY vt.date DESC";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
            conn.disconnect();
        }

        return transactionList;
    }

    public boolean cancelVictualTransaction(VictualTransaction transaction, int userId, String email) {
        if (refundVictualTrxBalance(transaction, userId) && returnStock(transaction.getItems(), transaction.getStationID()) && deleteTransactionItems(transaction.getTransactionID())) {
            conn.connect();

            String query = "DELETE FROM victuals_transaction WHERE transaction_id = ?";

            try {
                PreparedStatement stmt = conn.con.prepareStatement(query);
                stmt.setInt(1, transaction.getTransactionID());
                stmt.executeUpdate();

                NotificationController notificationController = new NotificationController();
                notificationController.sendNotification(email, "Victuals Cancelation", "Hi ---, we have successfully canceled your victual transaction and returned Rp " + transaction.getTotal() + " to your wallet.\n\nThank you!");
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                conn.disconnect();
            }

            return true;
        }

        return false;
    }

    public boolean removeTransactionItem(int victualID) {
        conn.connect();

        String query = "UPDATE transaction_item SET victual_id = NULL WHERE victual_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, victualID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
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
        conn.connect();

        String query = "DELETE FROM transaction_item WHERE transaction_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, transactionID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean refundVictualTrxBalance(VictualTransaction transaction, int userId) {
        conn.connect();

        String query = "UPDATE wallet SET balance = balance + ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setDouble(1, transaction.getTotal());
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }
}
