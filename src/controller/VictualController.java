package controller;

import model.classes.Victual;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VictualController {
    ConnectionHandler conn = new ConnectionHandler();

    public List<Victual> listVictual(int stationId) {
        conn.connect();
        List<Victual> victuals = new ArrayList<>();
        String query = "SELECT * FROM stock WHERE station_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, stationId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                victuals.add(getVictual(rs.getInt("victual_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return victuals;
    }

    public Victual getVictual(int victualId) {
        conn.connect();
        Victual victual = null;
        String query = "SELECT * FROM victual WHERE victual_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, victualId);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                victual = new Victual(rs.getInt("victual_id"), rs.getString("name"), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return victual;
    }

//    public boolean editVictual(Victual victual, File image) {
//        conn.connect();
//
//        String query = "UPDATE victual SET name = ?, price = ? WHERE victual_id = ?";
//
//        try {
//            PreparedStatement stmt = conn.con.prepareStatement(query);
//            stmt.setString(1, victual.getName());
//            stmt.setDouble(2, victual.getPrice());
//            stmt.setInt(3, victual.getId());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            conn.disconnect();
//        }
//
//        return true;
//    }

    public boolean editVictual(HashMap<String, Object > field, Victual oldVictual) {
        File image = (File) field.get("image");

        conn.connect();

        if (oldVictual.getImage().equals(image.getName())) {
            String query = "UPDATE victual SET name = ?, price = ? WHERE victual_id = ?";

            try {
                PreparedStatement stmt = conn.con.prepareStatement(query);
                stmt.setString(1, (String) field.get("name"));
                stmt.setDouble(2, (double) field.get("price"));
                stmt.setInt(3, (int) field.get("victual_id"));

                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                conn.disconnect();
            }
        } else {
            String fileName = ""; //Generate image name + pindahin ntar :)
        }

        return true;
    }

    public boolean addVictual(Victual victual, File image) {
        conn.connect();
        String query = "INSERT INTO victual (name, price, picture) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, victual.getName());
            stmt.setDouble(2, victual.getPrice());
//            stmt.setInt(3, generateUUID(image));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }

    public boolean removeVictual(int victualId) {
        conn.connect();
        String query = "DELETE FROM victual WHERE victual_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, victualId);
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
                return rs.getInt("victual_id");
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
        }

        return true;
    }
}
