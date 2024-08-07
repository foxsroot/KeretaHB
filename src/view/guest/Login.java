package view.guest;

import controller.AuthenticationController;
import controller.AuthenticationHelper;
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
        buttonPanel.setBounds(43, 300, 350, 100);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(0, 0, 100, 40);
        buttonPanel.add(loginButton);

        loginButton.addActionListener(e -> {
            AuthenticationController controller = new AuthenticationController();
            String profile = profileField.getText();
            String password = passwordField.getText();
            int[] results = controller.login(profile, password);
            if (results == null) {
                passwordField.setText(null);
                String title = "Login Gagal.";
                String message = "Silahkan cek kembali username atau password Anda!";
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
            } else {
                AuthenticationHelper.getInstance().setUserId(results[0]);
                this.dispose();
                if (results[1] == 0) {
                    new PassengerMenu();
                } else {
                    new AdminMenu();
                }
            }
        });

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(100, 0, 100, 40);
        buttonPanel.add(registerButton);

        registerButton.addActionListener(e -> {
            this.dispose();
            new Register();
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(200, 0, 100, 40);
        buttonPanel.add(backButton);

        backButton.addActionListener(e -> {
            this.dispose();
            new GuestMenu();
        });

        add(screenTitle);
        add(formLogin);
        add(buttonPanel);
    }

    public static void main(String[] args) {
        new Login();
    }
}
