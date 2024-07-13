package view.passenger.transaction;

import controller.AuthenticationHelper;
import controller.WalletController;
import view.admin.AdminMenu;
import view.guest.Login;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class WalletTopUpScreen extends JFrame {

    public WalletTopUpScreen() {
        initComponents();
        int userId = AuthenticationHelper.getInstance().getUserId();
        if (userId == 0) {
            this.dispose();
            new Login();
        } else {
            this.setVisible(true);
        }
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Top Up Wallet");

        JLabel screenTitle = new JLabel("Top Up Wallet");
        screenTitle.setFont(new Font("calibri", Font.BOLD, 20));
        screenTitle.setBounds(350, 10, 200, 30);

        JLabel amountLabel = new JLabel("Enter Amount:");
        amountLabel.setFont(new Font("calibri", Font.BOLD, 20));
        amountLabel.setBounds(20, 50, 200, 30);
        add(amountLabel);

        NumberFormat format = NumberFormat.getNumberInstance();
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Double.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(0.0);

        JFormattedTextField amountField = new JFormattedTextField(numberFormatter);
        amountField.setFont(new Font("calibri", Font.BOLD, 20));
        amountField.setBounds(250, 50, 200, 30);
        add(amountField);

        JButton topUpButton = new JButton("Top Up");
        topUpButton.setBounds(470, 50, 100, 30);
        add(topUpButton);

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(30, 610, 200, 30);
        add(exitButton);

        exitButton.addActionListener(e -> {
            dispose();
            new AdminMenu();
        });

        WalletController controller = new WalletController();

        topUpButton.addActionListener(e -> {
            try {
                amountField.commitEdit();
                double amount = ((Number) amountField.getValue()).doubleValue();
                if (controller.topUpBalance(amount, AuthenticationHelper.getInstance().getUserId())) {  // Assuming topUpBalance returns a boolean
                    JOptionPane.showMessageDialog(null, "Successfully topped up wallet", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to top up wallet", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (java.text.ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid amount entered", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(screenTitle);
    }
}
