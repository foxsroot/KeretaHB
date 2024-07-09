package view.admin.loyalty;

import controller.LoyaltyController;
import model.enums.LoyaltyEnum;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;

public class LoyaltyManagementScreen extends JFrame {
    JPanel formPanel = new JPanel();

    public LoyaltyManagementScreen() {
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Loyalty Management");

        JLabel screenTitle = new JLabel("Loyalty Management");
        screenTitle.setFont(new Font("calibri", Font.BOLD, 20));
        screenTitle.setBounds(350, 10, 200, 30);

        LoyaltyController controller = new LoyaltyController();
        ArrayList<LoyaltyEnum> loyalties = controller.getLoyalties();

        JComboBox loyaltyComboBox = new JComboBox(loyalties.toArray());
        loyaltyComboBox.setFont(new Font("calibri", Font.BOLD, 20));
        loyaltyComboBox.setBounds(20, 50, 200, 30);

        generateFormPanel((LoyaltyEnum) loyaltyComboBox.getSelectedItem());

        loyaltyComboBox.addActionListener(e -> {
            LoyaltyEnum loyalty = (LoyaltyEnum) loyaltyComboBox.getSelectedItem();
            formPanel.removeAll();
            generateFormPanel(loyalty);
            revalidate();
            repaint();
        });

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(30, 610, 200, 30);

        exitButton.addActionListener(e -> {
            dispose();
            //balik ke main menu
        });

        add(loyaltyComboBox);
        add(screenTitle);
        add(exitButton);
    }

    private void generateFormPanel(LoyaltyEnum loyalty) {
        LoyaltyController controller = new LoyaltyController();

        formPanel.setLayout(null);
        formPanel.setBounds(10, 100,800, 500);

        JLabel discountLabel = new JLabel("Discount                           : ");
        discountLabel.setFont(new Font("calibri", Font.BOLD, 20));
        discountLabel.setBounds(20, 50, 250, 30);
        formPanel.add(discountLabel);

        NumberFormat format = NumberFormat.getNumberInstance();
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Double.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(0.0);
        numberFormatter.setMaximum(1.0);

        JFormattedTextField discountField = new JFormattedTextField(numberFormatter);
        discountField.setFont(new Font("calibri", Font.BOLD, 20));
        discountField.setBounds(250, 50, 100, 30);
        discountField.setColumns(9);
        discountField.setText(String.valueOf(controller.getLoyaltyDiscount(loyalty)));
        formPanel.add(discountField);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(360, 50, 100, 30);
        formPanel.add(updateButton);

        updateButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Update this loyalty?", "Update Loyalty Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION && controller.updateLoyaltyDiscount(loyalty, discountField.getText())) {
                JOptionPane.showMessageDialog(null, "Successfully updated loyalty discount", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        add(formPanel);
    }

    public static void main(String[] args) {
        new LoyaltyManagementScreen();
    }
}
