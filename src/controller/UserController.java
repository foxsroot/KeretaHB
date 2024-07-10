package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class UserController {
    ConnectionHandler conn = new ConnectionHandler();

    public boolean updateProfile(int userId, String changeProfile, String status, int selectColumn) {
        conn.connect();

        if (selectColumn == 0) {
            String query = "UPDATE ? SET name = ? WHERE user_id = ?";

            try {
                PreparedStatement stmt = conn.con.prepareStatement(query);
                stmt.setString(1, status);
                stmt.setString(2, changeProfile);
                stmt.setInt(3, userId);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                conn.disconnect();
            }
            return true;
        } else if (selectColumn == 1) {
            String query = "UPDATE ? SET email = ? WHERE user_id = ?";

            try {
                PreparedStatement stmt = conn.con.prepareStatement(query);
                stmt.setString(1, status);
                stmt.setString(2, changeProfile);
                stmt.setInt(3, userId);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                conn.disconnect();
            }
            return true;
        }
        return false;
    }

    public boolean changePassword(int userId, String newPassword, String status) {
        conn.connect();
        String query = "UPDATE ? SET password = ? WHERE user_id = ?";
        newPassword = hashingPassword(newPassword);

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, status);
            stmt.setString(2, newPassword);
            stmt.setInt(3, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }
        return true;
    }

    public HashMap<String, String> getListUser() {
        conn.connect();

        HashMap<String, String> profileUser = new HashMap<>();
        String[] tables = {"admin, passenger"};
        for (String table : tables) {
            String queryGetAdmin = "SELECT name, email FROM ?";
            try {
                PreparedStatement stmt = conn.con.prepareStatement(queryGetAdmin);
                stmt.setString(1, table);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    profileUser.put(rs.getString("name"), rs.getString("email"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            } finally {
                conn.disconnect();
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
