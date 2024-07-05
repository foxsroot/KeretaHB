package controller;

import model.classes.Cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CartController {
    ConnectionHandler conn = new ConnectionHandler();

    public boolean addToCart(int userId, int victualId, int amount) {
        conn.connect();
        String query = "INSERT INTO cart_item (user_id, victual_id, amount) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, victualId);
            stmt.setInt(3, amount);
            stmt.executeUpdate();
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
            String query = "DELETE FROM cart WHERE victual_id = ? AND user_id = ?";

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
            String query = "UPDATE cart SET amount = ? WHERE victual_id = ? AND user_id = ?";

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

    public Cart getCart(int userId) {
        conn.connect();
        HashMap<Integer, Integer> victuals = new HashMap<>();

        String query = "SELECT * FROM cart_item WHERE user_id=?";

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                victuals.put(rs.getInt("victual_id"), rs.getInt("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        Cart cart = new Cart(victuals);
        cart.setTotalPrice(cart.calculateTotalPrice());

        return cart;
    }
}
