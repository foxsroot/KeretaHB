package view;

import model.classes.Notification;

import javax.swing.*;

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
    }
}
