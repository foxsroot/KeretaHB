package view.admin.train;

import controller.AuthenticationHelper;
import controller.CarriageController;
import model.classes.Carriage;
import model.enums.CarriageType;
import model.enums.ClassType;
import view.guest.Login;

import javax.swing.*;
import java.awt.*;

public class CarriageDetailScreen extends JFrame {
    private final CarriageController carriageController = new CarriageController();
    private final Carriage carriage;

    public CarriageDetailScreen(Carriage carriage) {
        this.carriage = carriage;

        this.setTitle("Carriage Detail");
        this.setSize(600, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        addCarriageDetails(mainPanel, carriage);
        addButtons(mainPanel);

        this.add(mainPanel);

        int userId = AuthenticationHelper.getInstance().getUserId();
        if (userId == 0) {
            this.dispose();
            new Login();
        } else {
            this.setVisible(true);
        }
    }

    private void addCarriageDetails(JPanel panel, Carriage carriage) {
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new GridLayout(6, 2, 10, 10));
        detailPanel.setBackground(Color.WHITE);

        detailPanel.add(createLabel("Carriage ID"));
        detailPanel.add(createValueLabel(String.valueOf(carriage.getId())));

        detailPanel.add(createLabel("Train ID"));
        detailPanel.add(createValueLabel(String.valueOf(carriage.getTrain_id())));

        detailPanel.add(createLabel("Carriage Type"));
        detailPanel.add(createValueLabel(carriage.getType().toString()));

        detailPanel.add(createLabel("Class Type"));
        detailPanel.add(createValueLabel(carriage.getCarriageClass().toString()));

        detailPanel.add(createLabel("Capacity"));
        detailPanel.add(createValueLabel(String.valueOf(carriage.getCapacity())));

        detailPanel.add(createLabel("Baggage Allowance"));
        detailPanel.add(createValueLabel(String.valueOf(carriage.getBaggageAllowance())));

        panel.add(detailPanel);
    }

    private void addButtons(JPanel panel) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton editButton = new JButton("Edit");
        editButton.setFont(new Font("Calibri", Font.PLAIN, 16));
        editButton.addActionListener(e -> {
            new AddEditCarriageScreen(carriage);
            this.dispose();
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Calibri", Font.PLAIN, 16));
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this carriage?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (carriageController.deleteCarriage(carriage)) {
                    JOptionPane.showMessageDialog(this, "Carriage deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new MenuTrain();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete carriage.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Calibri", Font.PLAIN, 16));
        backButton.addActionListener(e -> {
            new MenuTrain();
            this.dispose();
        });

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.PLAIN, 18));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    public static void main(String[] args) {
        Carriage carriage = new Carriage(123, CarriageType.EATERY, 500, ClassType.BUSINESS);
        new CarriageDetailScreen(carriage);
    }
}
