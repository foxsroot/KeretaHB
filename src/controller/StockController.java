package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public int getStock(int victual_id, int station_id) {
        conn.connect();

        String query = "SELECT * FROM stock WHERE victual_id = ? AND station_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, victual_id);
            stmt.setInt(2, station_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                return rs.getInt("stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return -1;
    }

    public boolean updateStock(int victualId, int stationId, int stock) {
        conn.connect();

        String query = "UPDATE stock SET stock = ? WHERE victual_id = ? AND station_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, stock);
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

    public boolean addStock(int victualId, int stationId, int stock) {
        conn.connect();

        String query = "UPDATE stock SET stock = stock + ? WHERE victual_id = ? AND station_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, stock);
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

    public boolean insertNewStock(int victualId, int stationId, int initialStock) {
        conn.connect();

        String query = "INSERT INTO stock(victual_id, station_id, stock) VALUES (?, ?, ?) ";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, victualId);
            stmt.setInt(2, stationId);
            stmt.setInt(3, initialStock);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }

    public boolean removeFromStation(int victualId, int stationId) {
        conn.connect();

        String query = "DELETE FROM stock WHERE victual_id = ? AND station_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, victualId);
            stmt.setInt(2, stationId);
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
