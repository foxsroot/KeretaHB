package controller;

import config.DirectoryConfig;
import model.classes.Victual;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VictualController {
    public List<Victual> getVictualList() {
        ConnectionHandler.getInstance().connect();

        List<Victual> victualList = new ArrayList<>();
        String query = "SELECT * FROM victual";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Victual victual = new Victual();
                victual.setId(rs.getInt("victual_id"));
                victual.setPrice(rs.getDouble("price"));
                victual.setImage(rs.getString("picture"));
                victual.setName(rs.getString("name"));
                victual.setDescription(rs.getString("description"));
                victualList.add(victual);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return victualList;
    }

    public boolean verifyForm(String name, String price, String description, File photo) {
        if (name != null && price != null && description != null && photo != null) {
            if (!name.isEmpty() && !price.isEmpty() && !description.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public HashMap<Victual, Integer> listVictual(int stationId) {
        ConnectionHandler.getInstance().connect();

        HashMap<Victual, Integer> victuals = new HashMap<>();
        String query = "SELECT vict.*, stck.stock FROM victual vict JOIN stock stck ON vict.victual_id = stck.victual_id WHERE station_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
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
            ConnectionHandler.getInstance().disconnect();
        }

        return victuals;
    }

    public Victual getVictual(int victualId) {
        ConnectionHandler.getInstance().connect();
        Victual victual = null;
        String query = "SELECT * FROM victual WHERE victual_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, victualId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                victual = new Victual(rs.getInt("victual_id"), rs.getString("name"), rs.getString("picture"), rs.getDouble("price"), rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return victual;
    }

    public boolean editVictual(Victual oldVictual, String name, String price, String description, File picture) {
        price = price.replaceAll(",", "");

        double priceValue = 0;

        try {
            priceValue = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            return false;
        }

        ConnectionHandler.getInstance().connect();

        if (oldVictual.getImage().equals(picture.getName())) {
            String query = "UPDATE victual SET name = ?, price = ?, description = ? WHERE victual_id = ?";

            try {
                PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setDouble(2, priceValue);
                stmt.setString(3, description);
                stmt.setInt(4, oldVictual.getId());

                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                ConnectionHandler.getInstance().disconnect();
            }
        } else {
            ImageController.deleteImage(DirectoryConfig.VICTUAL_IMAGES + oldVictual.getImage());

            String fileName = ImageController.generateName(picture);

            if (ImageController.saveImage(picture, fileName, DirectoryConfig.VICTUAL_IMAGES)) {
                String query = "UPDATE victual SET name = ?, price = ?, description = ?, picture = ? WHERE victual_id = ?";

                try {
                    PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
                    stmt.setString(1, name);
                    stmt.setDouble(2, priceValue);
                    stmt.setString(3, description);
                    stmt.setString(4, fileName);
                    stmt.setInt(5, oldVictual.getId());
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    ConnectionHandler.getInstance().disconnect();
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
        ConnectionHandler.getInstance().connect();

        if (ImageController.saveImage(image, fileName, DirectoryConfig.VICTUAL_IMAGES)) {
            String query = "INSERT INTO victual (name, price, picture, description) VALUES (?, ?, ?, ?)";

            try {
                PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
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
                ConnectionHandler.getInstance().disconnect();
            }
        }

        return false;
    }

    public boolean removeVictual(Victual victual) {
        TransactionController transactionController = new TransactionController();
        StockController stockController = new StockController();

        if (transactionController.removeTransactionItem(victual.getId()) && stockController.removeFromAllStation(victual.getId()) && ImageController.deleteImage(DirectoryConfig.VICTUAL_IMAGES + victual.getImage())) {
            ConnectionHandler.getInstance().connect();
            String query = "DELETE FROM victual WHERE victual_id = ?";

            try {
                PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
                stmt.setInt(1, victual.getId());
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionHandler.getInstance().disconnect();
            }
        }

        return false;
    }
}
