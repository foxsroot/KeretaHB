package view.passenger;

import controller.AuthenticationHelper;
import view.guest.Login;
import view.passenger.notification.ListNotificationScreen;
import view.passenger.profile.ProfileUser;
import view.passenger.schedule.ListScheduleScreen;
import view.passenger.transaction.TransactionHistoryScreen;
import view.passenger.transaction.ViewCartScreen;
import view.passenger.transaction.WalletTopUpScreen;
import view.passenger.victual.ListVictualScreen;

import javax.swing.*;
import java.awt.*;

public class PassengerMenu extends JFrame {
    public PassengerMenu() {
        int userId = AuthenticationHelper.getInstance().getUserId();
        if (userId == 0) {
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
            new ListScheduleScreen();
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

        JButton viewCart = new JButton("View Cart");
        viewCart.setBounds(0, 200, 860, 40);
        listMenu.add(viewCart);

        viewCart.addActionListener(e -> {
            this.dispose();
	        new ViewCartScreen();
        });

        JButton profile = new JButton("Profile");
        profile.setBounds(0, 250, 860, 40);
        listMenu.add(profile);

        profile.addActionListener(e -> {
            this.dispose();
            new ProfileUser(AuthenticationHelper.getInstance().getUserId());
        });

        JButton logout = new JButton("Logout");
        logout.setBounds(0, 300, 860, 40);
        listMenu.add(logout);

        logout.addActionListener(e -> {
            this.dispose();
            new Login();
        });

        JButton exit = new JButton("Exit");
        exit.setBounds(0, 350, 860, 40);
        listMenu.add(exit);

        exit.addActionListener(e -> {
            this.dispose();
        });

        JButton topupWallet = new JButton("Top Up Wallet");
        topupWallet.setBounds(20, 20, 150, 30);
        add(topupWallet);

        topupWallet.addActionListener(e -> {
            this.dispose();
            new WalletTopUpScreen();
        });

        add(screenTitle);
        add(listMenu);
    }

    public static void main(String[] args) {
        int userId = AuthenticationHelper.getInstance().getUserId();
        if (userId != 0){
            new PassengerMenu();
        } else {
            new Login();
        }
    }
}