package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockController {
    ConnectionHandler conn = new ConnectionHandler();

    public boolean deductStock(int qty, int victualId, int stationId) {
        conn.connect();

        String query = "UPDATE stock SET stock = stock - ? WHERE victual_id = ? AND station_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, qty);
            stmt.setInt(2, victualId);
            stmt.setInt(3, stationId);
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
