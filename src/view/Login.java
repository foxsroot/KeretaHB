package view;

import controller.AuthenticationController;
import view.admin.AdminMenu;
import view.passenger.PassengerMenu;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    public Login() {
        initComponents();
        this.setVisible(true);
    }

    public void initComponents() {
        this.setSize(400, 410);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Login!");

        JLabel screenTitle = new JLabel("Login");
        screenTitle.setFont(new Font("calibri", Font.BOLD, 44));
        screenTitle.setBounds(143, 22, 200, 51);

        JPanel formLogin = new JPanel();
        formLogin.setLayout(null);
        formLogin.setBounds(44, 111, 300, 150);

        JLabel profileUser = new JLabel("Username/Email:");
        profileUser.setFont(new Font("calibri", Font.PLAIN, 17));
        profileUser.setBounds(88, 0, 130, 30);
        formLogin.add(profileUser);

        JTextField profileField = new JTextField(255);
        profileField.setFont(new Font("calibri", Font.PLAIN, 15));
        profileField.setBounds(0, 33, 298, 30);
        formLogin.add(profileField);

        JLabel passwordUser = new JLabel("Password:");
        passwordUser.setFont(new Font("calibri", Font.PLAIN, 17));
        passwordUser.setBounds(113, 77, 130, 30);
        formLogin.add(passwordUser);

        JTextField passwordField = new JTextField(255);
        passwordField.setFont(new Font("calibri", Font.PLAIN, 15));
        passwordField.setBounds(0, 110, 298, 30);
        formLogin.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(86, 300, 350, 100);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(0, 0, 100, 40);
        buttonPanel.add(loginButton);

        loginButton.addActionListener(e -> {
            AuthenticationController controller = new AuthenticationController();
            String profile = profileField.getText();
            String password = passwordField.getText();
            int[] results = controller.login(profile, password);
            if (results[1] == -1) {
                String title = "";
                String message = "";
                if (results[0] == 0) {
                    title = "User tidak ditemukan";
                    message = "Tidak dapat ditemukan username atau email: " + profile;
                } else {
                    title = "Error!";
                    message = "Database sekarang sedang tidak berfungsi.";
                }
                passwordField.setText(null);
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
            } else if (results[1] == 0) {
                PassengerMenu passengerMenu = new PassengerMenu();
                this.dispose();
            } else {
                AdminMenu adminMenu = new AdminMenu();
                this.dispose();
                adminMenu.setVisible(true);
            }
        });

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(110, 0, 100, 40);
        buttonPanel.add(registerButton);

        add(screenTitle);
        add(formLogin);
        add(buttonPanel);
    }

    public static void main(String[] args) {
        new Login();
    }
}