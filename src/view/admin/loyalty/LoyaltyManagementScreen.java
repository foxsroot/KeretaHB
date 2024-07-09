//package view.admin.loyalty;
//
//import controller.LoyaltyController;
//import model.enums.LoyaltyEnum;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.ArrayList;
//
//public class LoyaltyManagementScreen extends JFrame {
//    public LoyaltyManagementScreen() {
//        initComponents();
//        this.setVisible(true);
//    }
//
//    private void initComponents() {
//        this.setSize(900, 700);
//        this.setResizable(false);
//        this.setLayout(null);
//        this.setLocationRelativeTo(null);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setTitle("Loyalty Management");
//
//        JLabel screenTitle = new JLabel("Loyalty Management");
//        screenTitle.setFont(new Font("calibri", Font.BOLD, 20));
//        screenTitle.setBounds(350, 10, 200, 30);
//
//        LoyaltyController controller = new LoyaltyController();
//        ArrayList<LoyaltyEnum> loyalties = controller.getLoyalties();
//
//        JComboBox loyaltyComboBox = new JComboBox(loyalties.toArray());
//        loyaltyComboBox.setFont(new Font("calibri", Font.BOLD, 20));
//        loyaltyComboBox.setBounds(20, 50, 200, 30);
//
//        generateFormPanel((LoyaltyEnum) loyaltyComboBox.getSelectedItem());
//
//        loyaltyComboBox.addActionListener(e -> {
//            LoyaltyEnum loyalty = (LoyaltyEnum) loyaltyComboBox.getSelectedItem();
//            generateFormPanel(loyalty);
//            revalidate();
//            repaint();
//        });
//
//        add(loyaltyComboBox);
//        add(screenTitle);
//    }
//
//    private void generateFormPanel(LoyaltyEnum loyalty) {
//        LoyaltyController controller = new LoyaltyController();
//
//        JPanel formPanel = new JPanel();
//        formPanel.setLayout(null);
//        formPanel.setBounds(10, 100,800, 500);
//
//        JLabel discountLabel = new JLabel("Discount                           : ");
//        discountLabel.setFont(new Font("calibri", Font.BOLD, 20));
//        discountLabel.setBounds(20, 50, 250, 30);
//        formPanel.add(discountLabel);
//
//        JTextField discountField = new JTextField();
//        discountField.setFont(new Font("calibri", Font.BOLD, 20));
//        discountField.setBounds(250, 50, 100, 30);
//        discountField.setText(String.valueOf(controller.getLoyaltyDiscount(loyalty)));
//        formPanel.add(discountField);
//
//        JLabel minimumTransactionLabel = new JLabel("Minimum Transaction     : ");
//        minimumTransactionLabel.setFont(new Font("calibri", Font.BOLD, 20));
//        minimumTransactionLabel.setBounds(20, 100, 250, 30);
//        formPanel.add(minimumTransactionLabel);
//
//        JTextField minimumTransactionField = new JTextField();
//        minimumTransactionField.setFont(new Font("calibri", Font.BOLD, 20));
//        minimumTransactionField.setBounds(250, 100, 250, 30);
//        minimumTransactionField.setText(String.valueOf(controller.getMinimumTransaction(loyalty)));
//        formPanel.add(minimumTransactionField);
//
//        JButton updateButton = new JButton("Update");
//        updateButton.setBounds(400, 150, 100, 30);
//        formPanel.add(updateButton);
//
//        updateButton.addActionListener(e -> {
//            int confirm = JOptionPane.showConfirmDialog(null, "Update this loyalty?", "Update Loyalty Confirmation", JOptionPane.YES_NO_OPTION);
//
////            if (confirm == JOptionPane.YES_OPTION) {
////                controller.updateLoyaltyRules(loyalty, discountField.getText(), minimumTransactionField.getText());
////            }
//        });
//
//        add(formPanel);
//    }
//
//    public static void main(String[] args) {
//        new LoyaltyManagementScreen();
//    }
//}
