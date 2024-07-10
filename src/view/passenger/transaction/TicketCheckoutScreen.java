package view.passenger.transaction;

import controller.LoyaltyController;
import controller.TransactionController;
import model.classes.Schedule;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TicketCheckoutScreen extends JFrame {
    private Schedule schedule;
    private JLabel totalLabel;
    private JLabel passengerLabel;
    private JComboBox<String> commuteBox;
    private int passengerCount = 1;
    private NumberFormat currencyFormat;
    double loyaltyDiscount;

    public TicketCheckoutScreen(Schedule schedule) {
        this.schedule = schedule;
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        currencyFormat.setMaximumFractionDigits(0);
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Ticket Checkout");

        LoyaltyController loyaltyController = new LoyaltyController();
        loyaltyDiscount = loyaltyController.getLoyaltyDiscount(loyaltyController.getLoyalty(2)); //Ganti ke singleton yg lg login

        JLabel screenTitle = new JLabel("Ticket Checkout");
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        screenTitle.setBounds(350, 20, 200, 30);
        add(screenTitle);

        JLabel dateLabel = new JLabel("Departure Date  : " + new SimpleDateFormat("dd MMMM yyyy (hh:mm)").format(schedule.getDepartureDate()));
        dateLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        dateLabel.setBounds(50, 150, 400, 30);
        add(dateLabel);

        JLabel priceLabel = new JLabel("Price                     : " + formatCurrency(schedule.getFee()));
        priceLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        priceLabel.setBounds(50, 200, 400, 30);
        add(priceLabel);

        JLabel passengerTextLabel = new JLabel("Passengers          : ");
        passengerTextLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        passengerTextLabel.setBounds(50, 250, 150, 30);
        add(passengerTextLabel);

        passengerLabel = new JLabel(String.valueOf(passengerCount));
        passengerLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        passengerLabel.setBounds(210, 250, 50, 30);
        add(passengerLabel);

        JButton decreaseButton = new JButton("-");
        decreaseButton.setBounds(270, 250, 45, 30);
        decreaseButton.addActionListener(e -> {
            if (passengerCount > 1) {
                passengerCount--;
                updatePassengerCount();
            }
        });
        add(decreaseButton);

        JButton increaseButton = new JButton("+");
        increaseButton.setBounds(320, 250, 45, 30);
        increaseButton.addActionListener(e -> {
            if (passengerCount < 10) {
                passengerCount++;
                updatePassengerCount();
            }
        });
        add(increaseButton);

        JLabel commuteLabel = new JLabel("Commute            :");
        commuteLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        commuteLabel.setBounds(50, 300, 150, 30);
        add(commuteLabel);

        commuteBox = new JComboBox<>(new String[]{"YES", "NO"});
        commuteBox.setFont(new Font("Calibri", Font.BOLD, 20));
        commuteBox.setBounds(210, 300, 300, 30);
        add(commuteBox);

        commuteBox.addActionListener(e -> {
            if (commuteBox.getSelectedItem().equals("YES")) {
                addCommutePrice();
            } else {
                updateTotalPrice();
            }
        });

        totalLabel = new JLabel("Total Price: " + formatCurrency(schedule.getFee()));
        totalLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        totalLabel.setBounds(50, 450, 350, 30);
        add(totalLabel);

        JButton purchaseButton = new JButton("Purchase");
        purchaseButton.setBounds(700, 630, 100, 30);
        purchaseButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, totalLabel.getText() + " will be deducted from balance, proceed?", "Purchase Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                TransactionController controller = new TransactionController();

                if (controller.bookTicket(2, schedule.getScheduleID(), passengerCount, commuteBox.getSelectedItem().toString().equals("YES"), parseTotalPrice(totalLabel.getText()))) {
                    JOptionPane.showMessageDialog(null, "Purchase Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to book ticket", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(purchaseButton);

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(30, 630, 150, 30);
        exitButton.addActionListener(e -> {
            dispose();
            // Baliks ke main menu :)
        });
        add(exitButton);
    }

    private double parseTotalPrice(String text) {
        return Double.parseDouble(text.replace("Total Price: Rp ", "").replaceAll("\\D", "").trim());
    }

    private void addCommutePrice() {
        try {
            double price = schedule.getFee() + 0.65 * schedule.getFee();
            double totalPrice = price * passengerCount;
            totalPrice -= totalPrice * loyaltyDiscount;
            totalLabel.setText("Total Price: " + formatCurrency(totalPrice));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void updateTotalPrice() {
        try {
            double price = schedule.getFee();
            double totalPrice = price * passengerCount;
            totalPrice -= totalPrice * loyaltyDiscount;
            totalLabel.setText("Total Price: " + formatCurrency(totalPrice));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void updatePassengerCount() {
        passengerLabel.setText(String.valueOf(passengerCount));
        if (commuteBox.getSelectedItem().equals("YES")) {
            addCommutePrice();
        } else {
            updateTotalPrice();
        }
    }

    private String formatCurrency(double amount) {
        return currencyFormat.format(amount);
    }
}
