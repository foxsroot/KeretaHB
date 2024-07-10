package view.passenger.transaction;

import controller.TransactionController;
import model.classes.Schedule;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.text.NumberFormat;

public class TicketCheckoutScreen extends JFrame {
    private Schedule schedule;
    private JLabel totalLabel;
    private JFormattedTextField passengerField;
    private JComboBox<String> commuteBox;

    public TicketCheckoutScreen(Schedule schedule) {
        this.schedule = schedule;
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

        JLabel screenTitle = new JLabel("Ticket Checkout");
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        screenTitle.setBounds(350, 20, 200, 30);
        add(screenTitle);

        JLabel dateLabel = new JLabel("Departure Time: " + schedule.getDepartureDate());
        dateLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        dateLabel.setBounds(50, 150, 400, 30);
        add(dateLabel);

        JLabel priceLabel = new JLabel("Price: Rp " + schedule.getFee());
        priceLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        priceLabel.setBounds(50, 200, 400, 30);
        add(priceLabel);

        JLabel passengerLabel = new JLabel("Passengers: ");
        passengerLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        passengerLabel.setBounds(50, 250, 400, 30);
        add(passengerLabel);

        NumberFormat format = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(1);
        numberFormatter.setMaximum(10);

        passengerField = new JFormattedTextField(numberFormatter);
        passengerField.setFont(new Font("Calibri", Font.PLAIN, 18));
        passengerField.setBounds(230, 250, 300, 30);
        passengerField.setColumns(9);
        passengerField.setText(String.valueOf(1));
        add(passengerField);

        passengerField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTotalPrice();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTotalPrice();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTotalPrice();
            }
        });

        JLabel commuteLabel = new JLabel("Commute:");
        commuteLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        commuteLabel.setBounds(50, 300, 400, 30);
        add(commuteLabel);

        commuteBox = new JComboBox<>(new String[]{"YES", "NO"});
        commuteBox.setFont(new Font("Calibri", Font.BOLD, 20));
        commuteBox.setBounds(230, 300, 300, 30);
        add(commuteBox);

        commuteBox.addActionListener(e -> {
            updateTotalPrice();
        });

        totalLabel = new JLabel("Total Price: Rp " + schedule.getFee());
        totalLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        totalLabel.setBounds(50, 450, 350, 30);
        add(totalLabel);

        JButton purchaseButton = new JButton("Purchase");
        purchaseButton.setBounds(700, 630, 100, 30);
        purchaseButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, totalLabel.getText() + " will be deducted from balance, proceed?", "Purchase Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                TransactionController controller = new TransactionController();

                if (controller.bookTicket(2, schedule.getScheduleID(), Integer.parseInt(passengerField.getText()), commuteBox.getSelectedItem().toString().equals("YES"))) {
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
            // Go back to main menu
        });
        add(exitButton);
    }

    private void updateTotalPrice() {
        try {
            int passengers = Integer.parseInt(passengerField.getText().replaceAll(",", ""));
            double price = schedule.getFee();
            double totalPrice = price * passengers;
            totalLabel.setText("Total Price: Rp " + totalPrice);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
