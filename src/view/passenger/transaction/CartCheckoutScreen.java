package view.passenger.transaction;

import controller.CartController;
import controller.ImageController;
import controller.VictualController;
import model.classes.Passenger;
import model.classes.Victual;

import javax.swing.*;
import java.awt.*;

public class CartCheckoutScreen extends JFrame {
    Passenger passenger;
    double total = 0;
    int stationId;

    public CartCheckoutScreen(int stationId) {
        this.stationId = stationId;
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
        screenTitle.setBounds(400, 20, 200, 20);

        int yOffset = 30;

        JPanel itemPanel = new JPanel();

        for (int victual_id : passenger.getCart().getVictual().keySet()) {
            itemPanel.add(createVictualPanel(victual_id, 0, yOffset));
            yOffset += 110;
        }

        JScrollPane scrollPane = new JScrollPane(itemPanel);
        scrollPane.setBounds(10, 50, 870, 550);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(20, yOffset + 10, 850, 10);

        itemPanel.add(separator);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(700, 630, 100, 30);

        checkoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Rp " + total + " will be deducted from balance, proceed?", "Purchase Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                CartController controller = new CartController();
                if (controller.checkout(passenger, stationId, total)) {
                    JOptionPane.showMessageDialog(null, "Purchase Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
        add(itemPanel);
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
        victualImage.setBounds(10, 10, 70, 90);
        victualImage.setIcon(new ImageIcon(ImageController.resizeImage("D:\\College\\Semester\\SP_2-3\\PBO\\KeretaHB\\assets\\dummy\\images.png", 70, 90)));
        victualPanel.add(victualImage);

        JLabel victualName = new JLabel(victual.getName());
        victualName.setFont(new Font("calibri", Font.BOLD, 17));
        victualName.setBounds(80, 10, 100, 20);
        victualPanel.add(victualName);

        JLabel victualPrice = new JLabel("Rp " + victual.getPrice());
        victualPrice.setFont(new Font("calibri", Font.PLAIN, 15));
        victualPrice.setBounds(80, 30, 100, 20);
        victualPanel.add(victualPrice);

        JLabel victualQuantity = new JLabel("Qty: x" + qty);
        victualQuantity.setFont(new Font("calibri", Font.BOLD, 17));
        victualQuantity.setBounds(250, 10, 100, 20);
        victualPanel.add(victualQuantity);

        double totalPrice = qty * victual.getPrice();
        total += totalPrice;

        JLabel price = new JLabel("Rp " + totalPrice);
        price.setFont(new Font("calibri", Font.BOLD, 20));
        price.setBounds(400, 30, 100, 30);
        victualPanel.add(price);

        return victualPanel;
    }
}
