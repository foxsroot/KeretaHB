package view.admin.train;

import controller.CarriageController;
import model.classes.Carriage;
import model.enums.CarriageType;
import model.enums.ClassType;
import view.admin.AdminMenu;

import javax.swing.*;
import java.awt.*;

public class AddEditCarriageScreen extends JFrame {
    CarriageController carriageController = new CarriageController();

    public AddEditCarriageScreen(Carriage carriage) {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Carriage Form");

        String formTitle;
        if (carriage.getId() != null) {
            formTitle = "Edit Carriage ID: " + carriage.getId();
        } else {
            formTitle = "Add Carriage";
        }

        JLabel screenTitle = new JLabel(formTitle);
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 20));
        screenTitle.setBounds(370, 10, 200, 30);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(20, 70, 850, 300);

        JLabel typeLabel = new JLabel("Type");
        typeLabel.setFont(new Font("Calibri", Font.PLAIN, 17));
        typeLabel.setBounds(0, 0, 130, 30);
        formPanel.add(typeLabel);

        JComboBox<CarriageType> typeComboBox = new JComboBox<>(CarriageType.values());
        typeComboBox.setFont(new Font("Calibri", Font.PLAIN, 15));
        typeComboBox.setBounds(200, 0, 300, 30);
        if (carriage.getType() != null) {
            typeComboBox.setSelectedItem(carriage.getType());
        }
        formPanel.add(typeComboBox);

        JLabel capacityLabel = new JLabel("Capacity");
        capacityLabel.setFont(new Font("Calibri", Font.PLAIN, 17));
        capacityLabel.setBounds(0, 50, 130, 30);
        formPanel.add(capacityLabel);

        JTextField capacityField = new JTextField(String.valueOf(carriage.getCapacity() > 0 ? carriage.getCapacity() : ""));
        capacityField.setFont(new Font("Calibri", Font.PLAIN, 15));
        capacityField.setBounds(200, 50, 300, 30);
        formPanel.add(capacityField);

        JLabel classLabel = new JLabel("Class");
        classLabel.setFont(new Font("Calibri", Font.PLAIN, 17));
        classLabel.setBounds(0, 100, 130, 30);
        formPanel.add(classLabel);

        JComboBox<ClassType> classComboBox = new JComboBox<>(ClassType.values());
        classComboBox.setFont(new Font("Calibri", Font.PLAIN, 15));
        classComboBox.setBounds(200, 100, 300, 30);
        if (carriage.getCarriageClass() != null) {
            classComboBox.setSelectedItem(carriage.getCarriageClass());
        }
        formPanel.add(classComboBox);

        JLabel baggageAllowanceLabel = new JLabel("Baggage Allowance");
        baggageAllowanceLabel.setFont(new Font("Calibri", Font.PLAIN, 17));
        baggageAllowanceLabel.setBounds(0, 150, 150, 30);
        formPanel.add(baggageAllowanceLabel);

        JTextField baggageAllowanceField = new JTextField(String.valueOf(carriage.getBaggageAllowance() > 0 ? carriage.getBaggageAllowance() : ""));
        baggageAllowanceField.setFont(new Font("Calibri", Font.PLAIN, 15));
        baggageAllowanceField.setBounds(200, 150, 300, 30);
        formPanel.add(baggageAllowanceField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(50, 400, 800, 50);

        JButton addButton = new JButton("Save Carriage");
        addButton.setBounds(510, 0, 150, 40);
        buttonPanel.add(addButton);

        addButton.addActionListener(e -> {
            try {
                String type = typeComboBox.getSelectedItem().toString();
                int capacity = Integer.parseInt(capacityField.getText());
                String carriageClass = classComboBox.getSelectedItem().toString();
                int baggageAllowance = Integer.parseInt(baggageAllowanceField.getText());

                if (carriageController.validateCarriageForm(type, capacity, carriageClass, baggageAllowance)) {
                    Carriage newCarriage = new Carriage(capacity, CarriageType.valueOf(type.toUpperCase()), baggageAllowance, ClassType.valueOf(carriageClass.toUpperCase()));
                    if (carriage.getId() != null) {
                        newCarriage.setId(carriage.getId());
                        if (carriageController.addCarriage(newCarriage, false)) {
                            JOptionPane.showMessageDialog(null, "Carriage Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error Updating Carriage!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        newCarriage.setTrain_id(0);
                        if (carriageController.addCarriage(newCarriage, true)) {
                            JOptionPane.showMessageDialog(null, "Carriage Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error Adding Carriage!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "All Fields Must Be Filled!", "Input Error!", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Input!", "Input Error!", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(110, 0, 150, 40);
        buttonPanel.add(exitButton);

        exitButton.addActionListener(e -> {
            new MenuTrain();
            this.dispose();
        });

        JLabel warningLabel = new JLabel("*Note: All fields must be filled!");
        warningLabel.setFont(new Font("Calibri", Font.BOLD, 13));
        warningLabel.setForeground(new Color(255, 0, 10));
        warningLabel.setBounds(50, 500, 170, 30);

        add(screenTitle);
        add(formPanel);
        add(buttonPanel);
        add(warningLabel);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        Carriage carriage = new Carriage(0, null, 0, null);
        AddEditCarriageScreen screen = new AddEditCarriageScreen(carriage);
    }
}
