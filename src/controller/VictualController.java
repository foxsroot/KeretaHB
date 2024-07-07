package controller;

import config.DirectoryConfig;
import model.classes.Victual;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class VictualController {
    ConnectionHandler conn = new ConnectionHandler();

    public boolean verifyForm(String name, String price, String description, File photo) {
        if (name != null && price != null && description != null && photo != null) {
            if (!name.isEmpty() && !price.isEmpty() && !description.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public HashMap<Victual, Integer> listVictual(int stationId) {
        conn.connect();

        HashMap<Victual, Integer> victuals = new HashMap<>();
        String query = "SELECT vict.*, stck.stock FROM victual vict JOIN stock stck ON vict.victual_id = stck.victual_id WHERE station_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, stationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Victual victual = new Victual();
                victual.setId(rs.getInt("victual_id"));
                victual.setName(rs.getString("name"));
                victual.setImage(rs.getString("picture"));
                victual.setPrice(rs.getDouble("price"));
                victual.setDescription(rs.getString("description"));

                victuals.put(victual, rs.getInt("stock"));
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
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                victual = new Victual(rs.getInt("victual_id"), rs.getString("name"), rs.getString("picture"), rs.getDouble("price"), rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return victual;
    }

    public boolean editVictual(HashMap<String, Object > field) {
        File picture = (File) field.get("picture");
        Victual oldVictual = (Victual) field.get("oldVictual");

        conn.connect();

        if (oldVictual.getImage().equals(picture.getName())) {
            String query = "UPDATE victual SET name = ?, price = ? WHERE victual_id = ?";

            try {
                PreparedStatement stmt = conn.con.prepareStatement(query);
                stmt.setString(1, (String) field.get("name"));
                stmt.setDouble(2, (double) field.get("price"));
                stmt.setInt(3, oldVictual.getId());

                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                conn.disconnect();
            }
        } else {
            if (ImageController.saveImage(picture, picture.getName(), DirectoryConfig.VICTUAL_IMAGES)) {
                String query = "UPDATE victual SET name = ?, price = ?, picture = ? WHERE victual_id = ?";

                try {
                    PreparedStatement stmt = conn.con.prepareStatement(query);
                    stmt.setString(1, (String) field.get("name"));
                    stmt.setDouble(2, (double) field.get("price"));
                    stmt.setString(3, picture.getName());
                    stmt.setInt(4, oldVictual.getId());
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        return true;
    }

    public boolean addVictual(String name, String price, String description, File image) {
        price = price.replaceAll(",", "");

        double priceValue = 0;

        try {
            priceValue = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            return false;
        }

        String fileName = ImageController.generateName(image);
        conn.connect();

        if (ImageController.saveImage(image, fileName, DirectoryConfig.VICTUAL_IMAGES)) {
            String query = "INSERT INTO victual (name, price, picture, description) VALUES (?, ?, ?, ?)";

            try {
                PreparedStatement stmt = conn.con.prepareStatement(query);
                stmt.setString(1, name.toUpperCase());
                stmt.setDouble(2, priceValue);
                stmt.setString(3, fileName);
                stmt.setString(4, description);
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                conn.disconnect();
            }
        }

        return false;
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
        }

        return true;
    }
}
