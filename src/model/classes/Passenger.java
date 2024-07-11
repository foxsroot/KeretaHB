package model.classes;

import model.enums.LoyaltyEnum;

import java.util.ArrayList;

public class Passenger extends User {
    private ArrayList<Transaction> transactions;
    private Wallet wallet;
    private LoyaltyEnum loyalty;
    private double totalPaid;
    private ArrayList<Notification> notifications;
    private Cart cart;

    public Passenger(String name, String cellphone, String email, String password, Integer id,
                     ArrayList<Transaction> transactions, Wallet wallet, LoyaltyEnum loyalty,
                     double totalPaid, ArrayList<Notification> notifications, Cart cart) {
        super(name, cellphone, email, password, id);
        this.transactions = transactions;
        this.wallet = wallet;
        this.loyalty = loyalty;
        this.totalPaid = totalPaid;
        this.notifications = notifications;
        this.cart = cart;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public LoyaltyEnum getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(LoyaltyEnum loyalty) {
        this.loyalty = loyalty;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
