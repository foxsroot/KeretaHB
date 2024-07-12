package controller;

import model.classes.Cart;
import model.classes.Passenger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

public class CartController {
    public int countTotal(int userId) {
        int total = 0;
        Cart userCart = getCart(userId);
        for (Integer count : userCart.getVictual().keySet()) {
            total += userCart.getVictual().get(count);
        }
        return total;
    }

    public boolean addToCart(int userId, int victualId, int amount, int station_id) {
        ConnectionHandler.getInstance().connect();

        try {
            String checkOtherStationQuery = "SELECT * FROM cart_item WHERE user_id = ? AND station_id <> ?";
            PreparedStatement checkOtherStationStmt = ConnectionHandler.getInstance().con.prepareStatement(checkOtherStationQuery);
            checkOtherStationStmt.setInt(1, userId);
            checkOtherStationStmt.setInt(2, station_id);
            ResultSet rsOtherStation = checkOtherStationStmt.executeQuery();

            if (rsOtherStation.next()) {
                clearCart(userId);
                ConnectionHandler.getInstance().connect();
            }

            rsOtherStation.close();
            checkOtherStationStmt.close();

            String checkQuery = "SELECT * FROM cart_item WHERE user_id = ? AND victual_id = ? AND station_id = ?";
            PreparedStatement checkStmt = ConnectionHandler.getInstance().con.prepareStatement(checkQuery);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, victualId);
            checkStmt.setInt(3, station_id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int currentAmount = rs.getInt("amount");

                String updateQuery = "UPDATE cart_item SET amount = ? WHERE user_id = ? AND victual_id = ? AND station_id = ?";
                PreparedStatement updateStmt = ConnectionHandler.getInstance().con.prepareStatement(updateQuery);
                updateStmt.setInt(1, currentAmount + amount);
                updateStmt.setInt(2, userId);
                updateStmt.setInt(3, victualId);
                updateStmt.setInt(4, station_id);

                updateStmt.executeUpdate();
                updateStmt.close();
            } else {
                String insertQuery = "INSERT INTO cart_item (user_id, victual_id, station_id, amount) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = ConnectionHandler.getInstance().con.prepareStatement(insertQuery);
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
            ConnectionHandler.getInstance().disconnect();
        }
        return true;
    }

    public boolean removeFromCart(int victualId, int userId) {
        ConnectionHandler.getInstance().connect();

        String query = "DELETE FROM cart_item WHERE user_id = ? AND victual_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, victualId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }

    public boolean editCart(int victualId, int user_id, int amount) {
        ConnectionHandler.getInstance().connect();

        if (amount <= 0) {
            String query = "DELETE FROM cart_item WHERE victual_id = ? AND user_id = ?";

            try {
                PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
                stmt.setInt(1, victualId);
                stmt.setInt(2, user_id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                ConnectionHandler.getInstance().disconnect();
            }
        } else {
            String query = "UPDATE cart_item SET amount = ? WHERE victual_id = ? AND user_id = ?";

            try {
                PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
                stmt.setInt(1, amount);
                stmt.setInt(2, victualId);
                stmt.setInt(3, user_id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                ConnectionHandler.getInstance().disconnect();
            }
        }

        return true;
    }

    public boolean checkout(Passenger passenger, double total) {
        int transaction_id = -1;
        StationController stationController = new StationController();

        if (!deductBalance(passenger, total) || !stationController.addStationIncome(passenger.getCart().getStationId(), total)) {
            return false;
        }

        ConnectionHandler.getInstance().connect();

        String query = "INSERT INTO victuals_transaction(user_id, station_id, date, total) VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, passenger.getId());
            stmt.setInt(2, passenger.getCart().getStationId());
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.setDouble(4, total);

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                transaction_id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        Cart cart = getCart(passenger.getId());

        if (transaction_id != -1) {
            for (Integer victual_id : cart.getVictual().keySet()) {
                StockController stockController = new StockController();

                if (!stockController.deductStock(cart.getVictual().get(victual_id), victual_id, passenger.getCart().getStationId())) {
                    return false;
                }

                ConnectionHandler.getInstance().connect();
                String itemQuery = "INSERT INTO transaction_item(transaction_id, victual_id, quantity) VALUES(?, ?, ?)";

                try {
                    PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(itemQuery);
                    stmt.setInt(1, transaction_id);
                    stmt.setInt(2, victual_id);
                    stmt.setInt(3, cart.getVictual().get(victual_id));

                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    ConnectionHandler.getInstance().disconnect();
                    return false;
                } finally {
                    ConnectionHandler.getInstance().disconnect();
                }
            }
        }

        clearCart(passenger.getId());
        return true;
    }

    public boolean verifyStock(Cart cart, int stationId) {
        HashMap<Integer, Integer> victuals = cart.getVictual();
        StockController stockController = new StockController();

        for (int victualId : victuals.keySet()) {
            if (stockController.getStock(victualId, stationId) < victuals.get(victualId)) {
                return false;
            }
        }

        return true;
    }

    private boolean deductBalance(Passenger passenger, double amount) {
        if (passenger.getWallet().getBalance() < amount) {
            return false;
        }

        ConnectionHandler.getInstance().connect();
        String query = "UPDATE wallet SET balance = ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setDouble(1, passenger.getWallet().getBalance() - amount);
            stmt.setInt(2, passenger.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
    }

    private void clearCart(int user_id) {
        ConnectionHandler.getInstance().connect();
        String query = "DELETE FROM cart_item WHERE user_id = ?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, user_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
    }

    public Cart getCart(int userId) {
        ConnectionHandler.getInstance().connect();
        HashMap<Integer, Integer> victuals = new HashMap<>();
        Integer stationId = -1;

        String query = "SELECT * FROM cart_item WHERE user_id=?";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                victuals.put(rs.getInt("victual_id"), rs.getInt("amount"));
                stationId = rs.getInt("station_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        Cart cart = new Cart(victuals, stationId);
        cart.setTotalPrice(cart.calculateTotalPrice());

        return cart;
    }
}
