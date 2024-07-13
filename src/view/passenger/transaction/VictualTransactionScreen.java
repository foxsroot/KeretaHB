package view.passenger.transaction;

import config.DirectoryConfig;
import controller.*;
import model.classes.Victual;
import model.classes.VictualTransaction;
import model.enums.VictualTransactionStatus;
import view.guest.Login;

import javax.swing.*;
import java.awt.*;

public class VictualTransactionScreen extends JFrame {
    VictualTransaction transaction;

    public VictualTransactionScreen(VictualTransaction transaction) {
        this.transaction = transaction;
        initComponents();
	    int userId = AuthenticationHelper.getInstance().getUserId();
	    if (userId == 0) {
		    this.dispose();
		    new Login();
	    } else {
		    this.setVisible(true);
	    }
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

        int xOffset = 5;
        int yOffset = 45;

        for (int victualId : transaction.getItems().keySet()) {
            try {
                Victual victual = controller.getVictual(victualId);
                historyPanel.add(createVictualPanel(victual, transaction.getItems().get(victual.getId()), xOffset, yOffset));
            } catch (NullPointerException e) {
                historyPanel.add(createVictualPanel(null, 0, xOffset, yOffset));
            } finally {
                yOffset += 205;
            }
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

        JLabel totalPriceLabel = new JLabel("Rp " + transaction.getTotal());
        totalPriceLabel.setFont(new Font("calibri", Font.BOLD, 20));
        totalPriceLabel.setBounds(580, yOffset - 35, 300, 30);
        historyPanel.add(totalPriceLabel);

        if (transaction.getStatus().equals(VictualTransactionStatus.PENDING)) {
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

            JButton claimButton = new JButton("Claim Victual");
            claimButton.setBounds(260, yOffset + 40, 200, 30);
            historyPanel.add(claimButton);

            claimButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(null, "WARNING: This button should be pressed after you claimed your victuals on station", "Claim victual?", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    TransactionController transactionController = new TransactionController();
                    if (transactionController.claimVictual(transaction.getTransactionID())) {
                        JOptionPane.showMessageDialog(null, "Transaction Claimed", "Claim Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                }
            });
        }

        if (transaction.getStatus().equals(VictualTransactionStatus.EXPIRED)) {
            JLabel expiredLabel = new JLabel("This Transaction is Expired");
            expiredLabel.setFont(new Font("calibri", Font.BOLD, 20));
            expiredLabel.setForeground(Color.RED);
            expiredLabel.setBounds(10, yOffset + 70, 350, 30);
            historyPanel.add(expiredLabel);
        }

        if (transaction.getStatus().equals(VictualTransactionStatus.CLAIMED)) {
            JLabel claimedLabel = new JLabel("You Claimed This Transaction Already");
            claimedLabel.setFont(new Font("calibri", Font.BOLD, 20));
            claimedLabel.setBounds(10, yOffset + 70, 350, 30);
            historyPanel.add(claimedLabel);
        }

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

        String imagePath = victual != null ? DirectoryConfig.VICTUAL_IMAGES + victual.getImage() : DirectoryConfig.ASSET_DIRECTORY + "dummy\\images.png";
        JLabel victualImage = new JLabel(imagePath);
        victualImage.setBounds(5, 5, 120, 150);
        victualImage.setIcon(new ImageIcon(ImageController.resizeImage(imagePath, 120, 150)));
        panel.add(victualImage);

        String victualName = victual != null ? victual.getName() : "Deleted Item";
        JLabel nameLabel = new JLabel(victualName);
        nameLabel.setFont(new Font("calibri", Font.BOLD, 20));
        nameLabel.setBounds(130, 10, 250, 20);
        panel.add(nameLabel);

        String victualPrice = victual != null ? "Rp " + victual.getPrice() : "Deleted Item";
        JLabel priceLabel = new JLabel(victualPrice);
        priceLabel.setFont(new Font("calibri", Font.PLAIN, 20));
        priceLabel.setBounds(130, 50, 150, 20);
        panel.add(priceLabel);

        String victualQuantity = victual != null ? "x" + quantity : "-";
        JLabel quantityLabel = new JLabel(victualQuantity);
        quantityLabel.setFont(new Font("calibri", Font.PLAIN, 20));
        quantityLabel.setBounds(400, 50, 150, 20);
        panel.add(quantityLabel);

        String price = victual != null ? String.valueOf(victual.getPrice() * quantity) : "-";

        JLabel totalPricePerItem = new JLabel("Rp " + price);
        totalPricePerItem.setFont(new Font("calibri", Font.PLAIN, 20));
        totalPricePerItem.setBounds(600, 50, 200, 20);
        panel.add(totalPricePerItem);

        return panel;
    }
}
