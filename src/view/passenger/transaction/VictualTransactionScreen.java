package view.passenger.transaction;

import config.DirectoryConfig;
import controller.ImageController;
import controller.StationController;
import controller.TransactionController;
import controller.VictualController;
import model.classes.Victual;
import model.classes.VictualTransaction;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class VictualTransactionScreen extends JFrame {
    VictualTransaction transaction;
    double totalPrice = 0;

    public VictualTransactionScreen(VictualTransaction transaction) {
        this.transaction = transaction;
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Transaction Details");

        JLabel screenTitle = new JLabel("Transaction Details");
        screenTitle.setFont(new Font("Arial", Font.BOLD, 20));
        screenTitle.setBounds(350, 10, 300, 30);

        VictualController controller = new VictualController();
        HashMap<Victual, Integer> victuals = new HashMap<>();

        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(null);
        historyPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("Item Name");
        nameLabel.setFont(new Font("calibri", Font.BOLD, 20));
        nameLabel.setBounds(85, 5, 200, 30);
        historyPanel.add(nameLabel);

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(new Font("calibri", Font.BOLD, 20));
        quantityLabel.setBounds(380, 5, 200, 30);
        historyPanel.add(quantityLabel);

        JLabel pricePerItemLabel = new JLabel("Price per item");
        pricePerItemLabel.setFont(new Font("calibri", Font.BOLD, 20));
        pricePerItemLabel.setBounds(600, 5, 200, 30);
        historyPanel.add(pricePerItemLabel);

        for (int victualId : transaction.getItems().keySet()) {
            Victual victual = controller.getVictual(victualId);
            victuals.put(victual, transaction.getItems().get(victualId));
        }

        int xOffset = 5;
        int yOffset = 45;

        for (Victual victual : victuals.keySet()) {
            historyPanel.add(createVictualPanel(victual, victuals.get(victual), xOffset, yOffset));
            yOffset += 205;
        }

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(10, yOffset, 760, 10);
        historyPanel.add(separator);

        StationController stationController = new StationController();
        yOffset += 20;

        JLabel stationLabel = new JLabel("Station Name: " + stationController.getStationById(transaction.getStationID()).getName());
        stationLabel.setFont(new Font("calibri", Font.BOLD, 18));
        stationLabel.setBounds(10, yOffset, 350, 30);
        historyPanel.add(stationLabel);

        yOffset += 35;
        JLabel transactionDate = new JLabel("Purchase Time: " + transaction.getDatePurchase());
        transactionDate.setFont(new Font("calibri", Font.BOLD, 18));
        transactionDate.setBounds(10, yOffset, 350, 30);
        historyPanel.add(transactionDate);

        JLabel totalPriceLabel = new JLabel("Rp " + totalPrice);
        totalPriceLabel.setFont(new Font("calibri", Font.BOLD, 20));
        totalPriceLabel.setBounds(580, yOffset - 35, 300, 30);
        historyPanel.add(totalPriceLabel);

        JButton cancelButton = new JButton("Cancel Transaction");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBounds(30, yOffset + 40, 200, 30);
        historyPanel.add(cancelButton);

        cancelButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(null, "Cancel this transaction?", "Cancel Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                TransactionController transactionController = new TransactionController();
                if (transactionController.cancelVictualTransaction(transaction, 2, "john.doe@example.com")) { //nanti ganti ke user id beneran (get dari singleton login)
                    JOptionPane.showMessageDialog(null, "Transaction Cancelled & Balance Refunded", "Cancel Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to Cancel Transaction", "Cancel Failure", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        historyPanel.setPreferredSize(new Dimension(800, yOffset + 100));

        JScrollPane scrollPane = new JScrollPane(historyPanel);
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

    private JPanel createVictualPanel(Victual victual, int quantity, int xOffset, int yOffset) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(null);
        panel.setBounds(xOffset, yOffset, 830, 200);

        JLabel victualImage = new JLabel(victual.getImage());
        victualImage.setBounds(5, 5, 120, 150);
        victualImage.setIcon(new ImageIcon(ImageController.resizeImage(DirectoryConfig.VICTUAL_IMAGES + victual.getImage(), 120, 150)));
        panel.add(victualImage);

        JLabel nameLabel = new JLabel(victual.getName());
        nameLabel.setFont(new Font("calibri", Font.BOLD, 20));
        nameLabel.setBounds(130, 10, 250, 20);
        panel.add(nameLabel);

        JLabel priceLabel = new JLabel("Rp " + victual.getPrice());
        priceLabel.setFont(new Font("calibri", Font.PLAIN, 20));
        priceLabel.setBounds(130, 50, 150, 20);
        panel.add(priceLabel);

        JLabel quantityLabel = new JLabel("x" + quantity);
        quantityLabel.setFont(new Font("calibri", Font.PLAIN, 20));
        quantityLabel.setBounds(400, 50, 150, 20);
        panel.add(quantityLabel);

        double itemPriceTotal = victual.getPrice() * quantity;

        JLabel totalPricePerItem = new JLabel("Rp " + itemPriceTotal);
        totalPricePerItem.setFont(new Font("calibri", Font.PLAIN, 20));
        totalPricePerItem.setBounds(600, 50, 200, 20);
        panel.add(totalPricePerItem);

        totalPrice += itemPriceTotal;

        return panel;
    }
}