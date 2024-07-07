package view.admin.train;

import controller.TrainController;
import model.classes.Train;

import javax.swing.*;
import java.awt.*;

public class AddTrainScreen extends JFrame {
    private final TrainController trainController = new TrainController();

    public AddTrainScreen(Train train) {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Add Train");

        JLabel screenTitle = new JLabel("Add Train Form");
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 20));
        screenTitle.setBounds(370, 10, 200, 30);
        add(screenTitle);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(20, 70, 850, 300);
        add(formPanel);

        JLabel trainSpeed = new JLabel("Train Speed");
        trainSpeed.setFont(new Font("Calibri", Font.PLAIN, 17));
        trainSpeed.setBounds(0, 0, 150, 30);
        formPanel.add(trainSpeed);

        JTextField trainSpeedField = new JTextField(train.getSpeed() != null ? train.getSpeed().toString() : "");
        trainSpeedField.setFont(new Font("Calibri", Font.PLAIN, 15));
        trainSpeedField.setBounds(200, 0, 300, 30);
        formPanel.add(trainSpeedField);

        JLabel trainStationId = new JLabel("Train Station ID");
        trainStationId.setFont(new Font("Calibri", Font.PLAIN, 17));
        trainStationId.setBounds(0, 50, 130, 30);
        formPanel.add(trainStationId);

        JTextField trainStationIdField = new JTextField(train.getStationId() != null ? train.getStationId().toString() : "");
        trainStationIdField.setFont(new Font("Calibri", Font.PLAIN, 15));
        trainStationIdField.setBounds(200, 50, 300, 30);
        formPanel.add(trainStationIdField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(50, 400, 800, 50);
        add(buttonPanel);

        JButton submitButton = new JButton("Add Train");
        submitButton.setBounds(510, 0, 150, 40);
        buttonPanel.add(submitButton);

        submitButton.addActionListener(e -> {
            try {
                Integer stationId = Integer.parseInt(trainStationIdField.getText());
                Integer speed = Integer.parseInt(trainSpeedField.getText());
                Train newTrain = null;

                if (trainController.validateTrainForm(stationId, speed)) {
                    if (train.getId() != null) {
                        newTrain = new Train(stationId, train.getCarriages(), speed);
                        if (trainController.addTrain(newTrain, false)) {
                            JOptionPane.showMessageDialog(null, "Train Edited Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "All Fields Must Be Filled!", "Input Error!", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        newTrain = new Train(stationId, null, speed);
                        if (trainController.addTrain(newTrain, true)) {
                            JOptionPane.showMessageDialog(null, "Train Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "All Fields Must Be Filled!", "Input Error!", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Input!", "Input Error!", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Input!", "Input Error!", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(110, 0, 150, 40);
        buttonPanel.add(exitButton);

        exitButton.addActionListener(e -> dispose());

        JLabel warningLabel = new JLabel("*Note: All fields must be filled!");
        warningLabel.setFont(new Font("Calibri", Font.BOLD, 13));
        warningLabel.setForeground(new Color(255, 0, 10));
        warningLabel.setBounds(50, 500, 170, 30);
        add(warningLabel);
    }
}
