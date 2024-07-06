package view.passenger;

import model.classes.Notification;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class ViewNotificationScreen extends JFrame {
    Notification notification;

    public ViewNotificationScreen(Notification notification) {
        this.notification = notification;
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("View Notification");

        JButton exitButton = new JButton("Back");
        exitButton.setBounds(50, 570, 75, 30);

        exitButton.addActionListener(e -> {
            dispose();
        });

        JLabel titleLabel = new JLabel(notification.getTitle());
        titleLabel.setFont(new Font("calibri", Font.BOLD, 20));
        titleLabel.setBounds(50, 20, 200, 30);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM, dd (HH:mm)");

        JLabel timeLabel = new JLabel("Received : " + dateFormat.format(notification.getReceivedDate()));
        timeLabel.setFont(new Font("calibri", Font.PLAIN, 14));
        timeLabel.setBounds(50, 50, 200, 30);

        JTextPane message = new JTextPane();
        message.setEditable(false);
        message.setText(notification.getMessage());
        message.setFont(new Font("Calibri", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(message);
        scrollPane.setBounds(50, 100, 800, 450);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(exitButton);
        add(titleLabel);
        add(timeLabel);
        add(scrollPane);
    }
}
