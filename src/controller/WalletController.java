package controller;

import model.classes.Wallet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletController {
    ConnectionHandler conn = new ConnectionHandler();

    public Wallet getWallet(int user_id) {
        conn.connect();

        Wallet wallet = null;

        String query = "SELECT * FROM wallet WHERE user_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int wallet_id = rs.getInt("wallet_id");
                double balance = rs.getDouble("balance");
                String pin = rs.getString("pin");

                wallet = new Wallet(wallet_id, balance, pin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            conn.disconnect();
        }

        return wallet;
    }
}
