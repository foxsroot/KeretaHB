package controller;

import model.classes.Wallet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletController {
    public Wallet getWallet(int user_id) {
        ConnectionHandler.getInstance().connect();

        Wallet wallet = null;

        String query = "SELECT * FROM wallet WHERE user_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
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
            ConnectionHandler.getInstance().disconnect();
        }

        return wallet;
    }
}
