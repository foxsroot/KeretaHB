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

    public boolean changePassword(int userId, String newPassword, String status) {
        ConnectionHandler.getInstance().connect();
        String query = "UPDATE ? SET password = ? WHERE user_id = ?";
        newPassword = hashingPassword(newPassword);

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setString(1, status);
            stmt.setString(2, newPassword);
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

    private static String hashingPassword(String password) {
        Random rand = new Random();
        int option = rand.nextInt(3);
        final int MAX_LENGTH = 100;
        String temp = "";

        if (option == 0) {
            loop:for (int i = password.length() / 2; i >= 0; i--) {
                for (int j = 0; j < password.length(); j++) {
                    if (temp.length() + 1 >= MAX_LENGTH) {
                        break loop;
                    }
                    temp += password.charAt(i);
                    if (temp.length() + 1 >= MAX_LENGTH) {
                        break loop;
                    }
                    if (j % 2 == 0) {
                        temp += Character.toUpperCase(password.charAt(j));
                    }
                }
            }
            temp += "j";
            return temp;
        }

        if (option == 1) {
            loop:for (int i = 0; i < password.length() / 2; i++) {
                for (int j = password.length() - 1; j >= 0; j--) {
                    if (temp.length() + 1 >= MAX_LENGTH) {
                        break loop;
                    }
                    temp += password.charAt(i);
                    if (temp.length() + 1 >= MAX_LENGTH) {
                        break loop;
                    }
                    if (j % (i + 1) == 0) {
                        temp += Character.toUpperCase(password.charAt(j));
                    }
                }
            }
            temp += "c";
            return temp;
        }

        loop:for (int i = password.length() - 1; i >= 0; i--) {
            for (int j = password.length() / 2; j >= 0; j--) {
                if (temp.length() + 1 >= MAX_LENGTH) {
                    break loop;
                }
                temp += password.charAt(j);
                if (temp.length() + 1 >= MAX_LENGTH) {
                    break loop;
                }
                if (j % (i + 1) == 1) {
                    temp += Character.toUpperCase(password.charAt(i));
                }
            }
        }
        temp += "a";
        return temp;
    }
}
