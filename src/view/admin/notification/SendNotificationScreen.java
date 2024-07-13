package view.admin.notification;

import controller.AuthenticationHelper;
import controller.NotificationController;
import view.admin.AdminMenu;
import view.guest.Login;

import javax.swing.*;
import java.awt.*;

public class SendNotificationScreen extends JFrame {
    public SendNotificationScreen() {
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
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Send Notification");

        JLabel screenTitle = new JLabel("Notification Form");
        screenTitle.setFont(new Font("calibri", Font.BOLD, 20));
        screenTitle.setBounds(370, 10, 200, 30);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(20, 70, 850, 300);

        JLabel recipientEmailLabel = new JLabel("Recipient Email   :");
        recipientEmailLabel.setFont(new Font("calibri", Font.PLAIN, 17));
        recipientEmailLabel.setBounds(0, 0, 130, 30);
        formPanel.add(recipientEmailLabel);

        JTextField emailField = new JTextField(255);
        emailField.setFont(new Font("calibri", Font.PLAIN, 15));
        emailField.setBounds(140, 0, 300, 30);
        formPanel.add(emailField);

        JLabel notificationTitle = new JLabel("Notification Title :");
        notificationTitle.setFont(new Font("calibri", Font.PLAIN, 17));
        notificationTitle.setBounds(0, 50, 130, 30);
        formPanel.add(notificationTitle);

        JTextField titleField = new JTextField(255);
        titleField.setFont(new Font("calibri", Font.PLAIN, 15));
        titleField.setBounds(140, 50, 350, 30);
        formPanel.add(titleField);

        JLabel messageLabel = new JLabel("Message               :");
        messageLabel.setFont(new Font("calibri", Font.PLAIN, 17));
        messageLabel.setBounds(0, 100, 130, 30);
        formPanel.add(messageLabel);

        JTextArea messageArea = new JTextArea();
        messageArea.setFont(new Font("calibri", Font.PLAIN, 15));
        messageArea.setLineWrap(true);
        messageArea.setBounds(140, 100, 500, 200);
        formPanel.add(messageArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(50, 400, 800, 50);

        JButton sendButton = new JButton("Send");
        sendButton.setBounds(510, 0, 100, 40);
        buttonPanel.add(sendButton);

        sendButton.addActionListener(e -> {
            NotificationController controller = new NotificationController();
            String email = emailField.getText();
            String title = titleField.getText();
            String message = messageArea.getText();

            if (controller.validateNotificationForm(email, title, message)) {
                if (controller.sendNotification(email, title, message)) {
                    JOptionPane.showMessageDialog(null, "Notification Sent!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    emailField.setText(null);
                    titleField.setText(null);
                    messageArea.setText(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed To Send Notification!\nPlease recheck the email", "Error to Send Notification", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "All Fields Must Be Filled!", "Input Error!", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(110, 0, 150, 40);
        buttonPanel.add(exitButton);

        exitButton.addActionListener(e -> {
            dispose();
            new AdminMenu();
        });

        JLabel warningLabel = new JLabel("*Note: All fields must be filled!");
        warningLabel.setFont(new Font("calibri", Font.BOLD, 13));
        warningLabel.setForeground(new Color(255, 0, 10));
        warningLabel.setBounds(50, 500, 170, 30);

        add(screenTitle);
        add(formPanel);
        add(buttonPanel);
        add(warningLabel);
    }

    public static void main(String[] args) {
        new SendNotificationScreen();
    }
}
