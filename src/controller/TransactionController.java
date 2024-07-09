package controller;

import model.classes.Transaction;
import model.classes.VictualTransaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TransactionController {
    ConnectionHandler conn = new ConnectionHandler();

    public ArrayList<Transaction> getVictualTransactionList(int userId) {
        conn.connect();

        ArrayList<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT vt.transaction_id, vt.user_id, vt.station_id, vt.date, ti.victual_id, ti.quantity FROM victuals_transaction vt LEFT JOIN transaction_item ti ON vt.transaction_id = ti.transaction_id WHERE vt.user_id = ? ORDER BY vt.date DESC";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VictualTransaction transaction = new VictualTransaction();
                transaction.setTransactionID(rs.getInt("transaction_id"));
                transaction.setStationID(rs.getInt("station_id"));
                transaction.setDatePurchase(rs.getTimestamp("date"));
//                transaction.setAmount(rs.getDouble("amount"));

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

    public boolean cancelVictualTransaction(int transactionID) {
        conn.connect();

        String query = "DELETE FROM victuals_transaction WHERE transaction_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, transactionID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }

//    private boolean refundBalance(int transactionID) {
//        conn.connect();
//
//        String query = "UPDATE wallet SET balance = ? WHERE transaction_id = ?";
//
//    }
}
