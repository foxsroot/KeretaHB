package controller;

import model.classes.Cart;
import model.classes.Passenger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

public class CartController {
    ConnectionHandler conn = new ConnectionHandler();

    public boolean addToCart(int userId, int victualId, int amount, int station_id) {
        conn.connect();

        try {
            String checkOtherStationQuery = "SELECT * FROM cart_item WHERE user_id = ? AND station_id <> ?";
            PreparedStatement checkOtherStationStmt = conn.con.prepareStatement(checkOtherStationQuery);
            checkOtherStationStmt.setInt(1, userId);
            checkOtherStationStmt.setInt(2, station_id);
            ResultSet rsOtherStation = checkOtherStationStmt.executeQuery();

            if (rsOtherStation.next()) {
                clearCart(userId);
            }

            rsOtherStation.close();
            checkOtherStationStmt.close();

            String checkQuery = "SELECT * FROM cart_item WHERE user_id = ? AND victual_id = ? AND station_id = ?";
            PreparedStatement checkStmt = conn.con.prepareStatement(checkQuery);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, victualId);
            checkStmt.setInt(3, station_id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int currentAmount = rs.getInt("amount");

                String updateQuery = "UPDATE cart_item SET amount = ? WHERE user_id = ? AND victual_id = ? AND station_id = ?";
                PreparedStatement updateStmt = conn.con.prepareStatement(updateQuery);
                updateStmt.setInt(1, currentAmount + amount);
                updateStmt.setInt(2, userId);
                updateStmt.setInt(3, victualId);
                updateStmt.setInt(4, station_id);

                updateStmt.executeUpdate();
                updateStmt.close();
            } else {
                String insertQuery = "INSERT INTO cart_item (user_id, victual_id, station_id, amount) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.con.prepareStatement(insertQuery);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, victualId);
                insertStmt.setInt(3, station_id);
                insertStmt.setInt(4, amount);

                insertStmt.executeUpdate();
                insertStmt.close();
            }

            rs.close();
            checkStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }
        return true;
    }

    public boolean editCart(int victualId, int user_id, int amount, Cart cart) {
        conn.connect();

        if (amount <= 0) {
            String query = "DELETE FROM cart_item WHERE victual_id = ? AND user_id = ?";

            try {
                PreparedStatement stmt = conn.con.prepareStatement(query);
                stmt.setInt(1, victualId);
                stmt.setInt(2, user_id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                conn.disconnect();
            }
        } else {
            String query = "UPDATE cart_item SET amount = ? WHERE victual_id = ? AND user_id = ?";

            try {
                PreparedStatement stmt = conn.con.prepareStatement(query);
                stmt.setInt(1, amount);
                stmt.setInt(1, victualId);
                stmt.setInt(2, user_id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                conn.disconnect();
            }
        }

        return true;
    }

    public boolean checkout(Passenger passenger, double total) {
        int transaction_id = -1;

        if (!deductBalance(passenger, total)) {
            return false;
        }

        conn.connect();

        String query = "INSERT INTO victuals_transaction(user_id, station_id, date) VALUES(?, ?, ?)";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, passenger.getId());
            stmt.setInt(2, passenger.getCart().getStationId());
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                transaction_id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        Cart cart = getCart(passenger.getId());

        if (transaction_id != -1) {
            for (Integer victual_id : cart.getVictual().keySet()) {
                StockController stockController = new StockController();

                if (!stockController.deductStock(cart.getVictual().get(victual_id), victual_id, passenger.getCart().getStationId())) {
                    return false;
                }

                conn.connect();
                String itemQuery = "INSERT INTO transaction_item(transaction_id, victual_id, quantity) VALUES(?, ?, ?)";

                try {
                    PreparedStatement stmt = conn.con.prepareStatement(itemQuery);
                    stmt.setInt(1, transaction_id);
                    stmt.setInt(2, victual_id);
                    stmt.setInt(3, cart.getVictual().get(victual_id));

                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    conn.disconnect();
                    return false;
                } finally {
                    conn.disconnect();
                }
            }
        }

        clearCart(passenger.getId());
        return true;
    }

    private boolean deductBalance(Passenger passenger, double amount) {
        if (passenger.getWallet().getBalance() < amount) {
            return false;
        }

        conn.connect();
        String query = "UPDATE wallet SET balance = ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setDouble(1, passenger.getWallet().getBalance() - amount);
            stmt.setInt(2, passenger.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }

        return true;
    }

    private void clearCart(int user_id) {
        conn.connect();
        String query = "DELETE FROM cart_item WHERE user_id = ?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, user_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public Cart getCart(int userId) {
        conn.connect();
        HashMap<Integer, Integer> victuals = new HashMap<>();
        Integer stationId = -1;

        String query = "SELECT * FROM cart_item WHERE user_id=?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                victuals.put(rs.getInt("victual_id"), rs.getInt("amount"));
                stationId = rs.getInt("station_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        Cart cart = new Cart(victuals, stationId);
        cart.setTotalPrice(cart.calculateTotalPrice());

        return cart;
    }
}
