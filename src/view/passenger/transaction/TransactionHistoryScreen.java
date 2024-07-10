package view.passenger.transaction;

import controller.StationController;
import controller.TransactionController;
import model.classes.Transaction;
import model.classes.VictualTransaction;
import model.classes.TicketTransaction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TransactionHistoryScreen extends JFrame {
    private JPanel historyPanel;
    private JComboBox<String> orderComboBox;

    public TransactionHistoryScreen() {
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Transaction History");

        orderComboBox = new JComboBox<>(new String[]{"Victual Transactions", "Ticket Transactions"});
        orderComboBox.setBounds(50, 10, 200, 30);
        orderComboBox.addActionListener(e -> updateTransactionHistory());

        historyPanel = new JPanel();
        historyPanel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane(historyPanel);
        scrollPane.setBounds(50, 50, 800, 530);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(50, 600, 150, 40);
        exitButton.addActionListener(e -> dispose());

        add(orderComboBox);
        add(scrollPane);
        add(exitButton);

        updateTransactionHistory();
    }

    private void updateTransactionHistory() {
        TransactionController controller = new TransactionController();
        ArrayList<Transaction> transactions = controller.getVictualTransactionList(2);

        String selectedItem = (String) orderComboBox.getSelectedItem();
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if ("Victual Transactions".equals(selectedItem) && transaction instanceof VictualTransaction) {
                filteredTransactions.add(transaction);
            } else if ("Ticket Transactions".equals(selectedItem) && transaction instanceof TicketTransaction) {
                filteredTransactions.add(transaction);
            }
        }

        historyPanel.removeAll();
        int xOffset = 15;
        int yOffset = 10;

        for (Transaction transaction : filteredTransactions) {
            historyPanel.add(createOrderPanel(transaction, xOffset, yOffset));
            yOffset += 210;
        }

        historyPanel.setPreferredSize(new Dimension(800, yOffset));
        historyPanel.revalidate();
        historyPanel.repaint();
    }

    private JPanel createOrderPanel(Transaction transaction, int xOffset, int yOffset) {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        if (transaction instanceof VictualTransaction) {
            panel.setBackground(Color.WHITE);
            panel.setBounds(xOffset, yOffset, 750, 200);

            JLabel panelTitle = new JLabel("Victual Transaction");
            panelTitle.setFont(new Font("calibri", Font.BOLD, 20));
            panelTitle.setBounds(10, 0, 200, 40);
            panel.add(panelTitle);

            JLabel timeStamp = new JLabel("Purchase Time : " + transaction.getDatePurchase().toString());
            timeStamp.setFont(new Font("calibri", Font.BOLD, 20));
            timeStamp.setBounds(10, 30, 350, 40);
            panel.add(timeStamp);

            StationController controller = new StationController();

            JLabel station = new JLabel("Station: " + controller.getStationById(((VictualTransaction) transaction).getStationID()).getName());
            station.setFont(new Font("calibri", Font.BOLD, 20));
            station.setBounds(10, 60, 350, 40);
            panel.add(station);

            JButton viewDetailsButton = new JButton("View Details");
            viewDetailsButton.setBounds(10, 110, 350, 40);
            panel.add(viewDetailsButton);

            viewDetailsButton.addActionListener(e -> {
                dispose();
                new VictualTransactionScreen((VictualTransaction) transaction);
            });

            JLabel totalLabel = new JLabel("Total Price");
            totalLabel.setFont(new Font("calibri", Font.BOLD, 20));
            totalLabel.setBounds(550, 55, 350, 40);
            panel.add(totalLabel);

            JLabel totalAmount = new JLabel("Rp " + transaction.getTotal());
            totalAmount.setFont(new Font("calibri", Font.BOLD, 20));
            totalAmount.setBounds(550, 80, 350, 40);
            panel.add(totalAmount);

        } else if (transaction instanceof TicketTransaction) {
            panel.setBackground(Color.LIGHT_GRAY);
            panel.setBounds(xOffset, yOffset, 750, 200);

            JLabel panelTitle = new JLabel("Ticket Transaction");
            panelTitle.setFont(new Font("calibri", Font.BOLD, 20));
            panelTitle.setBounds(10, 0, 200, 40);
            panel.add(panelTitle);

            JLabel timeStamp = new JLabel("Purchase Time : " + transaction.getDatePurchase().toString());
            timeStamp.setFont(new Font("calibri", Font.BOLD, 20));
            timeStamp.setBounds(10, 30, 350, 40);
            panel.add(timeStamp);

            JButton viewDetailsButton = new JButton("View Details");
            viewDetailsButton.setBounds(10, 110, 350, 40);
            panel.add(viewDetailsButton);

            viewDetailsButton.addActionListener(e -> {
                // Implement ticket transaction details screen
            });

            JLabel totalLabel = new JLabel("Total Price");
            totalLabel.setFont(new Font("calibri", Font.BOLD, 20));
            totalLabel.setBounds(550, 55, 350, 40);
            panel.add(totalLabel);

            JLabel totalAmount = new JLabel("Rp " + transaction.getTotal());
            totalAmount.setFont(new Font("calibri", Font.BOLD, 20));
            totalAmount.setBounds(550, 80, 350, 40);
            panel.add(totalAmount);
        }

        return panel;
    }

    public static void main(String[] args) {
        new TransactionHistoryScreen();
    }
}
