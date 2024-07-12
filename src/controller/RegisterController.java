package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class RegisterController {
    public String register(String name, String email, String password, String cellphone, String pin) {
        ConnectionHandler.getInstance().connect();
        String query = "SELECT * FROM passenger WHERE name = ? OR email = ?";
        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (name.equals(rs.getString("name"))) {
                    return "Username \"" + name + "\" sudah ada sebelumnya!";
                }
                if (email.equals(rs.getString("email"))) {
                    return "Email \"" + email + "\" sudah terpakai sebelumnya!";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        if (password.length() <= 4) {
            return "Buatlah password minimal 4 karakter!";
        }

        password = new PasswordEncoder().hash(password);
        ConnectionHandler.getInstance().connect();
        String queryInsert = "INSERT INTO passenger(email, password, name, cellphone) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, name);
            stmt.setString(4, cellphone);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                createWallet(rs.getInt(1), pin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Masukkan semua field!";
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }
        return "Berhasil melakukan registrasi!";
    }

    private boolean createWallet(int userID, String pin) {
        ConnectionHandler.getInstance().connect();

        String query = "INSERT INTO wallet (user_id, pin) VALUES (?, ?)";

        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(query);
            stmt.setInt(1, userID);
            stmt.setString(2, pin);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return true;
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