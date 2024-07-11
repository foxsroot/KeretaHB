package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationController {
    public int[] login(String userProfile, String userPassword) {
        ConnectionHandler.getInstance().connect();
        String[] tables = {"admin", "passenger"};
        for (String table : tables) {
            String queryGetAdmin = "SELECT user_id, name, email, password FROM ?";
            try {
                PreparedStatement stmt = ConnectionHandler.getInstance().con.prepareStatement(queryGetAdmin);
                stmt.setString(1, table);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    if (name.equals(userProfile) || email.equals(userProfile)) {
                        if (password.equals(userPassword)) {
                            if (table.equals(tables[1])) {
                                return new int[]{userId, 0};
                            } else {
                                return new int[]{userId, 1};
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return new int[]{-1, -1};
            } finally {
                ConnectionHandler.getInstance().disconnect();
            }
        }
        return new int[]{0, -1};
    }
    public boolean logout(int userId) {
        return true;
    }
}
