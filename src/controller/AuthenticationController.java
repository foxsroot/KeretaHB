package controller;

import view.guest.Login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationController {
    public int[] login(String userProfile, String userPassword) {
        ConnectionHandler.getInstance().connect();

        String queryGetPassenger = "SELECT user_id, name, email, password FROM passenger";
        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(queryGetPassenger);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                if (name.equals(userProfile) || email.equals(userProfile)) {
                    if (new PasswordEncoder().authenticate(userPassword.toCharArray(), password)) {
                        return new int[]{userId, 0};
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String queryGetAdmin = "SELECT user_id, name, email, password FROM admin";
        try {
            PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(queryGetAdmin);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                if (name.equals(userProfile) || email.equals(userProfile)) {
                    System.out.println(name);
                    System.out.println(userProfile);
                    if (password.equals(userPassword)) {
                        return new int[]{userId, 1};
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.getInstance().disconnect();
        }

        return null;
    }

    public void logout() {
        new AuthenticationHelper().reset();
        new Login();
    }
}
