package controller;

import model.classes.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class UserController {
    public Passenger getUser(int userId) {
        ConnectionHandler.getInstance().connect();

        String query = "SELECT * FROM passenger WHERE user_id = ?";
        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                return new Passenger(
                        rs.getString("name"),
                        rs.getString("cellphone"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("user_id"),
                        new TransactionController().getVictualTransactionList(userId),
                        new WalletController().getWallet(userId),
                        new LoyaltyController().getLoyalty(userId),
                        rs.getDouble("total_paid"),
                        new NotificationController().getNotifications(userId),
                        new CartController().getCart(userId)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return null;
    }

    public Admin getAdmin(int userId) {
        ConnectionHandler.getInstance().connect();

        String query = "SELECT * FROM admin WHERE user_id = ?";
        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                return new Admin(
                        rs.getString("name"),
                        rs.getString("cellphone"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("user_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return null;
    }

    public boolean updateProfile(int userId, String status, String[] changeProfile) {
        ConnectionHandler.getInstance().connect();

        if (changeProfile[0].isEmpty()) {
            String query = "UPDATE ? SET name = ? WHERE user_id = ?";

            try {
                PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
                stmt.setString(1, status);
                stmt.setString(2, changeProfile[0]);
                stmt.setInt(3, userId);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                ConnectionHandler.getInstance().disconnect();
            }
            return true;
        } else if (changeProfile[1].isEmpty()) {
            String query = "UPDATE ? SET email = ? WHERE user_id = ?";

            try {
                PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
                stmt.setString(1, status);
                stmt.setString(2, changeProfile[1]);
                stmt.setInt(3, userId);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                ConnectionHandler.getInstance().disconnect();
            }
            return true;
        } else {
            String query = "";
            if (status.equals("admin")) {
                query = "UPDATE admin SET name = ?, email = ? WHERE user_id = ?";
            } else {
                query = "UPDATE passenger SET name = ?, email = ? WHERE user_id = ?";
            }

            try {
                PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
                stmt.setString(1, changeProfile[0]);
                stmt.setString(2, changeProfile[1]);
                stmt.setInt(3, userId);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                ConnectionHandler.getInstance().disconnect();
            }
            return true;
        }
    }

    public boolean changePassword(int userId, String userPassword, String newPassword, String status) {
        ConnectionHandler.getInstance().connect();

        String queryGetPassenger = "SELECT password FROM passenger WHERE user_id = ?";
        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(queryGetPassenger);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (!new PasswordEncoder().authenticate(userPassword.toCharArray(), rs.getString("password"))) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ConnectionHandler.getInstance().connect();
        String query = "";
        if (status.equals("passenger")) {
            query = "UPDATE passenger SET password = ? WHERE user_id = ?";
        } else {
            query = "UPDATE admin SET password = ? WHERE user_id = ?";
        }
        newPassword = new PasswordEncoder().hash(newPassword);

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return true;
    }

    public HashMap<String, String> getListUser() {
        ConnectionHandler.getInstance().connect();

        HashMap<String, String> profileUser = new HashMap<>();
        String[] tables = {"admin, passenger"};
        for (String table : tables) {
            String queryGetAdmin = "SELECT name, email FROM ?";
            try {
                PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(queryGetAdmin);
                stmt.setString(1, table);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    profileUser.put(rs.getString("name"), rs.getString("email"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            } finally {
                ConnectionHandler.getInstance().disconnect();
            }
        }
        return profileUser;
    }
}
