package view.passenger.transaction;

import controller.CartController;
import controller.ImageController;
import controller.VictualController;
import controller.WalletController;
import model.classes.*;
import model.enums.LoyaltyEnum;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CartCheckoutScreen extends JFrame {
    Passenger passenger;
    double total = 0;

    public CartCheckoutScreen() {
        CartController controller = new CartController();
        WalletController walletController = new WalletController();
        //ambil passenger yg lg login
        passenger = new Passenger("John Doe", "1234567890", "john.doe@example.com", "password123", 2, new ArrayList<>(), walletController.getWallet(2), LoyaltyEnum.CLASSIC, 0, new ArrayList<>(), controller.getCart(2));
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Cart Checkout");

        JLabel screenTitle = new JLabel("Cart Checkout");
        screenTitle.setFont(new Font("calibri", Font.BOLD, 30));
        screenTitle.setBounds(350, 20, 200, 30);

        int yOffset = 30;

        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(null);

        for (int victual_id : passenger.getCart().getVictual().keySet()) {
            itemPanel.add(createVictualPanel(victual_id, passenger.getCart().getVictual().get(victual_id), yOffset));
            yOffset += 110;
        }

        itemPanel.setPreferredSize(new Dimension(870, yOffset + 120));

        JScrollPane scrollPane = new JScrollPane(itemPanel);
        scrollPane.setBounds(10, 50, 870, 550);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(20, yOffset + 10, 850, 10);

        itemPanel.add(separator);

        JLabel totalLabel = new JLabel("Total Price:");
        totalLabel.setFont(new Font("calibri", Font.BOLD, 30));
        totalLabel.setBounds(20, yOffset + 30, 350, 30);
        itemPanel.add(totalLabel);

        JLabel totalPriceLabel = new JLabel("Rp " + total);
        totalPriceLabel.setFont(new Font("calibri", Font.BOLD, 30));
        totalPriceLabel.setBounds(600, yOffset + 30, 350, 30);
        itemPanel.add(totalPriceLabel);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(700, 630, 100, 30);

        checkoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Rp " + total + " will be deducted from balance, proceed?", "Purchase Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                CartController controller = new CartController();
                if (controller.checkout(passenger, total)) {
                    JOptionPane.showMessageDialog(null, "Purchase Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Purchase Failed! Please Check your balance and try again", "Failure", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(30, 630, 100, 30);

        exitButton.addActionListener(e -> {
            dispose();
            //balik ke main menu
        });

        add(screenTitle);
        add(scrollPane);
        add(checkoutButton);
        add(exitButton);
    }

    private JPanel createVictualPanel(int victual_id, int qty, int yOffset) {
        JPanel victualPanel = new JPanel();
        victualPanel.setLayout(null);
        victualPanel.setBounds(10, yOffset, 850, 100);
        victualPanel.setBackground(Color.WHITE);

        VictualController controller = new VictualController();
        Victual victual = controller.getVictual(victual_id);

        JLabel victualImage = new JLabel();
        victualImage.setBounds(7, 7, 70, 85);
        victualImage.setIcon(new ImageIcon(ImageController.resizeImage("D:\\College\\Semester\\SP_2-3\\PBO\\KeretaHB\\assets\\dummy\\images.png", 70, 85)));
        victualPanel.add(victualImage);

        JLabel victualName = new JLabel(victual.getName());
        victualName.setFont(new Font("calibri", Font.BOLD, 25));
        victualName.setBounds(90, 15, 400, 30);
        victualPanel.add(victualName);

        JLabel victualPrice = new JLabel("Rp " + victual.getPrice());
        victualPrice.setFont(new Font("calibri", Font.PLAIN, 25));
        victualPrice.setBounds(90, 50, 200, 25);
        victualPanel.add(victualPrice);

        JLabel victualQuantity = new JLabel("Qty: x" + qty);
        victualQuantity.setFont(new Font("calibri", Font.BOLD, 20));
        victualQuantity.setBounds(420, 25, 100, 25);
        victualPanel.add(victualQuantity);

        JLabel totalLabel = new JLabel("ITEM TOTAL");
        totalLabel.setFont(new Font("calibri", Font.PLAIN, 15));
        totalLabel.setBounds(650, 20, 200, 30);
        victualPanel.add(totalLabel);

        double totalPrice = qty * victual.getPrice();
        total += totalPrice;

        JLabel price = new JLabel("Rp " + totalPrice);
        price.setFont(new Font("calibri", Font.BOLD, 20));
        price.setBounds(650, 45, 200, 30);
        victualPanel.add(price);

        return victualPanel;
    }
}
