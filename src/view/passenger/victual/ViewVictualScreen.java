package view.passenger.victual;

import config.DirectoryConfig;
import controller.*;
import model.classes.Victual;
import view.passenger.transaction.CartCheckoutScreen;
import view.passenger.transaction.ViewCartScreen;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class ViewVictualScreen extends JFrame {
    Victual victual;
    int stationId;

    public ViewVictualScreen(Victual victual, int stationId) {
        this.victual = victual;
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
        this.setTitle("View Victual");

        JButton exitButton = new JButton("Back to victual list");
        exitButton.setBounds(10, 10, 150, 20);

        exitButton.addActionListener(e -> {
            dispose();
            new ListVictualScreen();
        });

        JPanel itemPanel = new JPanel();
        itemPanel.setBackground(Color.WHITE);
        itemPanel.setLayout(null);

        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(30, 30, 250, 300);
        imageLabel.setIcon(new ImageIcon(ImageController.resizeImage(DirectoryConfig.VICTUAL_IMAGES + victual.getImage(), 250, 300)));
        itemPanel.add(imageLabel);

        JLabel itemName = new JLabel(victual.getName());
        itemName.setFont(new Font("calibri", Font.BOLD, 30));
        itemName.setBounds(300, 35, 250, 30);
        itemPanel.add(itemName);

        JLabel itemPrice = new JLabel("Rp " + victual.getPrice());
        itemPrice.setFont(new Font("calibri", Font.PLAIN, 27));
        itemPrice.setBounds(300, 70, 250, 30);
        itemPanel.add(itemPrice);

        StockController stockController = new StockController();
        int stock = stockController.getStock(victual.getId(), stationId);

        NumberFormat format = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(1);
        numberFormatter.setMaximum(stock);

        JPanel qtyPanel = new JPanel();
        qtyPanel.setLayout(null);
        qtyPanel.setBackground(null);
        qtyPanel.setBounds(300, 120, 120, 50);

        JFormattedTextField quantityField = new JFormattedTextField(numberFormatter);
        quantityField.setFont(new Font("calibri", Font.BOLD, 30));
        quantityField.setBounds(0, 0, 50, 50);
        quantityField.setColumns(5);
        quantityField.setValue(1);
        qtyPanel.add(quantityField);

        JButton incrementButton = new JButton("+");
        incrementButton.setBounds(55, 5, 50, 20);
        incrementButton.addActionListener(e -> {
            int value = ((Number) quantityField.getValue()).intValue();
            if (value < stock) {
                quantityField.setValue(value + 1);
            }
        });
        qtyPanel.add(incrementButton);

        JButton decrementButton = new JButton("-");
        decrementButton.setBounds(55, 25, 50, 20);
        decrementButton.addActionListener(e -> {
            int value = ((Number) quantityField.getValue()).intValue();
            if (value > 1) {
                quantityField.setValue(value - 1);
            }
        });
        qtyPanel.add(decrementButton);

        itemPanel.add(qtyPanel);

        JPanel purchasePanel = new JPanel();
        purchasePanel.setLayout(null);
        purchasePanel.setBackground(null);
        purchasePanel.setBounds(440, 125, 400, 50);

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setBounds(0, 0, 100, 40);
        purchasePanel.add(addToCartButton);

        addToCartButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Add " + victual.getName() + " to Cart?", "Add to Cart", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                CartController cartController = new CartController();
                if (cartController.addToCart(AuthenticationHelper.getInstance().getUserId(), victual.getId(), Integer.parseInt(quantityField.getText()), stationId)) {
                    JOptionPane.showMessageDialog(null, "Successfully added " + victual.getName() + " to Cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add item to the cart", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(115, 0, 100, 40);
        purchasePanel.add(checkoutButton);

        checkoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Add " + victual.getName() + " to Cart and Checkout?", "Checkout", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                CartController cartController = new CartController();
                if (cartController.addToCart(AuthenticationHelper.getInstance().getUserId(), victual.getId(), Integer.parseInt(quantityField.getText()), stationId)) {
                    dispose();
                    new ViewCartScreen();
                }
            }
        });

        itemPanel.add(purchasePanel);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(300, 185, 540, 10);
        itemPanel.add(separator);

        JLabel descriptionLabel = new JLabel("Product Description");
        descriptionLabel.setFont(new Font("calibri", Font.BOLD, 25));
        descriptionLabel.setBounds(300, 60, 500, 300);
        itemPanel.add(descriptionLabel);

        JTextArea description = new JTextArea();
        description.setEditable(false);
        description.setText(victual.getDescription());
        description.setLineWrap(true);
        description.setFont(new Font("calibri", Font.PLAIN, 20));
        description.setBounds(300, 240, 540, 500);
        itemPanel.add(description);

        JScrollPane scrollPane = new JScrollPane(itemPanel);
        scrollPane.setBounds(10, 50, 870, 550);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(exitButton);
        add(scrollPane);
    }
}
