package view.admin;

import controller.AuthenticationController;
import controller.AuthenticationHelper;
import view.admin.loyalty.LoyaltyManagementScreen;
import view.admin.notification.SendNotificationScreen;
import view.admin.schedule.MenuSchedule;
import view.admin.station.MenuStation;
import view.admin.train.MenuTrain;
import view.admin.victual.MenuVictual;
import view.guest.Login;

import javax.swing.*;
import java.awt.*;

public class AdminMenu extends JFrame {
    public AdminMenu() {
        int userId = AuthenticationHelper.getInstance().getUserId();
        if (userId == 0){
            this.dispose();
            new Login();
        } else {
            this.setVisible(true);
        }
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Menu");

        JLabel screenTitle = new JLabel("List Menu Admin");
        screenTitle.setFont(new Font("calibri", Font.BOLD, 40));
        screenTitle.setBounds(300, 30, 800, 50);

        JPanel listMenu = new JPanel();
        listMenu.setLayout(null);
        listMenu.setBounds(10, 100, 880, 680);

        JButton editLoyalty = new JButton("Edit Loyalty");
        editLoyalty.setBounds(0, 0, 860, 40);
        listMenu.add(editLoyalty);

        editLoyalty.addActionListener(e -> {
            this.dispose();
            new LoyaltyManagementScreen();
        });

        JButton sendNotification = new JButton("Send Notification");
        sendNotification.setBounds(0, 50, 860, 40);
        listMenu.add(sendNotification);

        sendNotification.addActionListener(e -> {
            this.dispose();
            new SendNotificationScreen();
        });

        JButton schedule = new JButton("Schedule");
        schedule.setBounds(0, 100, 860, 40);
        listMenu.add(schedule);

        schedule.addActionListener(e -> {
            this.dispose();
            new MenuSchedule();
        });

        JButton station = new JButton("Station");
        station.setBounds(0, 150, 860, 40);
        listMenu.add(station);

        station.addActionListener(e -> {
            this.dispose();
            new MenuStation();
        });

        JButton train = new JButton("Train");
        train.setBounds(0, 200, 860, 40);
        listMenu.add(train);

        train.addActionListener(e -> {
            this.dispose();
            new MenuTrain();
        });

        JButton victual = new JButton("Victual");
        victual.setBounds(0, 250, 860, 40);
        listMenu.add(victual);

        victual.addActionListener(e -> {
            this.dispose();
            new MenuVictual();
        });

        JButton logout = new JButton("Logout");
        logout.setBounds(0, 300, 860, 40);
        listMenu.add(logout);

        logout.addActionListener(e -> {
            this.dispose();
            new AuthenticationController().logout();
        });

        JButton exit = new JButton("Exit");
        exit.setBounds(0, 350, 860, 40);
        listMenu.add(exit);

        exit.addActionListener(e -> {
            this.dispose();
        });

        add(screenTitle);
        add(listMenu);
    }

    public static void main(String[] args) {
        new AdminMenu();
    }
}
