package view;

import controller.NotificationController;
import model.classes.Notification;
import model.classes.Passenger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListNotificationScreen extends JFrame {
    Passenger passenger;

    public ListNotificationScreen(Passenger passenger) {
        this.passenger = passenger;
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Notifications");

        NotificationController controller = new NotificationController();
        passenger.setNotifications(controller.getNotifications(passenger.getId()));
        ArrayList<Notification> notifications = passenger.getNotifications();

        JLabel screenTitle = new JLabel("Notification List");
        screenTitle.setFont(new Font("calibri", Font.BOLD, 20));
        screenTitle.setBounds(370, 10, 200, 30);

        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(null);

        int initialOffset = 10;

        for (Notification notification : notifications) {
            JPanel notificationRectangle = createNotificationPanel(notification, initialOffset);
            notificationPanel.add(notificationRectangle);
            initialOffset += 60;
        }

        notificationPanel.setPreferredSize(new Dimension(800, initialOffset));

        JScrollPane scrollPane = new JScrollPane(notificationPanel);
        scrollPane.setBounds(50, 50, 800, 530);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(50, 600, 150, 40);

        add(scrollPane);
        add(screenTitle);
        add(exitButton);
    }

    private JPanel createNotificationPanel(Notification notification, int offset) {
        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(null);
        notificationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        notificationPanel.setBounds(10, offset, 760, 50);

        JLabel titleLabel = new JLabel(notification.getTitle());
        titleLabel.setFont(new Font("calibri", Font.BOLD, 15));
        titleLabel.setBounds(10, 10, 760, 30);
        notificationPanel.add(titleLabel);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM, dd (HH:mm)");

        JLabel timeReceivedLabel = new JLabel(dateFormat.format(notification.getReceivedDate()));
        timeReceivedLabel.setFont(new Font("calibri", Font.BOLD, 13));
        timeReceivedLabel.setBounds(670, 30, 100, 20);
        notificationPanel.add(timeReceivedLabel);

        notificationPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                JFrame notificationFrame = new ViewNotificationScreen(notification);
                notificationFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        setVisible(true);
                    }
                });
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                notificationPanel.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                notificationPanel.setBackground(null);
            }
        });

        return notificationPanel;
    }

    public static void main(String[] args) {
        Passenger passenger1 = new Passenger("John Doe", "+6287885827270", "john.doe@example.com", "password123", 2, null, null, null, 0, null, null);
        new ListNotificationScreen(passenger1);
    }
}
