package view.passenger;

import controller.AuthenticationHelper;
import view.Login;
import view.admin.loyalty.LoyaltyManagementScreen;
import view.admin.notification.SendNotificationScreen;
import view.admin.profile.AdminProfile;
import view.admin.schedule.MenuSchedule;
import view.admin.station.MenuStation;
import view.admin.train.MenuTrain;
import view.admin.victual.ListVictualScreen;
import view.admin.victual.MenuVictual;
import view.passenger.notification.ListNotificationScreen;
import view.passenger.profile.ProfileUser;
import view.passenger.transaction.TicketCheckoutScreen;
import view.passenger.transaction.TransactionHistoryScreen;

import javax.swing.*;
import java.awt.*;

public class PassengerMenu extends JFrame {
    public PassengerMenu() {
        this.setVisible(true);
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Menu");

        JLabel screenTitle = new JLabel("List Menu Passenger");
        screenTitle.setFont(new Font("calibri", Font.BOLD, 40));
        screenTitle.setBounds(300, 30, 800, 50);

        JPanel listMenu = new JPanel();
        listMenu.setLayout(null);
        listMenu.setBounds(10, 100, 880, 680);

        JButton bookTicket = new JButton("Book Ticket");
        bookTicket.setBounds(0, 0, 860, 40);
        listMenu.add(bookTicket);

        bookTicket.addActionListener(e -> {
            this.dispose();
            new TicketCheckoutScreen(null);
        });

        JButton buyVictual = new JButton("Buy Victual");
        buyVictual.setBounds(0, 50, 860, 40);
        listMenu.add(buyVictual);

        buyVictual.addActionListener(e -> {
            this.dispose();
            new ListVictualScreen();
        });

        JButton checkNotification = new JButton("Check Notification");
        checkNotification.setBounds(0, 100, 860, 40);
        listMenu.add(checkNotification);

        checkNotification.addActionListener(e -> {
            this.dispose();
            new ListNotificationScreen();
        });

        JButton transactionHistory = new JButton("Transaction History");
        transactionHistory.setBounds(0, 150, 860, 40);
        listMenu.add(transactionHistory);

        transactionHistory.addActionListener(e -> {
            this.dispose();
            new TransactionHistoryScreen();
        });

        JButton profile = new JButton("Profile");
        profile.setBounds(0, 200, 860, 40);
        listMenu.add(profile);

        profile.addActionListener(e -> {
            this.dispose();
            new ProfileUser(AuthenticationHelper.getInstance().getUserId());
        });

        JButton logout = new JButton("Logout");
        logout.setBounds(0, 250, 860, 40);
        listMenu.add(logout);

        logout.addActionListener(e -> {
            this.dispose();
            new Login();
        });

        add(screenTitle);
        add(listMenu);
    }

    public static void main(String[] args) {
        new PassengerMenu();
    }
}