package view.admin.schedule;

import com.toedter.calendar.JDateChooser;
import controller.ScheduleController;
import controller.StationController;
import controller.TrainController;
import model.classes.Schedule;
import model.classes.Station;
import model.classes.Train;
import view.admin.AdminMenu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AddEditScheduleScreen extends JFrame {
    private final StationController stationController = new StationController();
    private final ScheduleController schController = new ScheduleController();
    private final JComboBox<Integer> trainList = new JComboBox<>();
    private final TrainController trainController = new TrainController();

    public AddEditScheduleScreen(Schedule schedule) {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Schedule Form");

        String formTitle;
        if (schedule.getScheduleID() != null) {
            formTitle = "Edit Schedule ID: " + schedule.getScheduleID();
        } else {
            formTitle = "Add Schedule";
        }

        JLabel screenTitle = new JLabel(formTitle);
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 20));
        screenTitle.setBounds(370, 10, 200, 30);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(20, 70, 850, 300);

        JLabel departureStation = new JLabel("Departure Station ID");
        departureStation.setFont(new Font("Calibri", Font.PLAIN, 17));
        departureStation.setBounds(0, 0, 150, 30);
        formPanel.add(departureStation);

        JComboBox<Integer> departureStationComboBox = new JComboBox<>();
        departureStationComboBox.setFont(new Font("Calibri", Font.PLAIN, 15));
        departureStationComboBox.setBounds(200, 0, 300, 30);
        loadStationList(departureStationComboBox, 0);
        if (schedule.getDeparture() != null) {
            departureStationComboBox.setSelectedItem(schedule.getDeparture());
        }
        formPanel.add(departureStationComboBox);


        JLabel arrivalStation = new JLabel("Arrival Station ID");
        arrivalStation.setFont(new Font("Calibri", Font.PLAIN, 17));
        arrivalStation.setBounds(0, 50, 130, 30);
        formPanel.add(arrivalStation);

        JComboBox<Integer> arrivalStationComboBox = new JComboBox<>();
        arrivalStationComboBox.setFont(new Font("Calibri", Font.PLAIN, 15));
        arrivalStationComboBox.setBounds(200, 50, 300, 30);
        if (schedule.getArrival() != null) {
            arrivalStationComboBox.setSelectedItem(schedule.getArrival());
        }
        formPanel.add(arrivalStationComboBox);

        departureStationComboBox.addActionListener(e -> {
            Integer selectedStationID = (Integer) departureStationComboBox.getSelectedItem();
            loadStationList(arrivalStationComboBox, selectedStationID);
            loadTrainList(selectedStationID);
        });

        JLabel departureDate = new JLabel("Departure Date");
        departureDate.setFont(new Font("Calibri", Font.PLAIN, 17));
        departureDate.setBounds(0, 100, 130, 30);
        formPanel.add(departureDate);

        JDateChooser departureDateChooser = new JDateChooser(schedule.getDepartureDate());
        departureDateChooser.setFont(new Font("Calibri", Font.PLAIN, 17));
        departureDateChooser.setBounds(200, 100, 300, 30);
        formPanel.add(departureDateChooser);

        JLabel trainChooser = new JLabel("Choose Train");
        trainChooser.setFont(new Font("Calibri", Font.PLAIN, 17));
        trainChooser.setBounds(0, 150, 130, 30);
        formPanel.add(trainChooser);

        trainList.setBounds(200, 150, 300, 30);
        if (schedule.getDeparture() != null) {
            loadTrainList(schedule.getDeparture());
            if (schedule.getTrainID() != null) {
                trainList.setSelectedItem(schedule.getTrainID());
            }
        }

        trainList.addActionListener(e -> {
            if (trainController.totalTrainCapacity((Integer) trainList.getSelectedItem()) == 0) {
                JOptionPane.showMessageDialog(this, "This train did not have any carriages. Please choose another train.");
                trainList.setSelectedIndex(-1);
            }
        });
        formPanel.add(trainList);

        JLabel fee = new JLabel("Set Fee");
        fee.setFont(new Font("Calibri", Font.PLAIN, 17));
        fee.setBounds(0, 200, 130, 30);
        formPanel.add(fee);

        JTextField feeField = new JTextField(String.valueOf(schedule.getFee() != 0 ? schedule.getFee() : ""));
        feeField.setFont(new Font("Calibri", Font.PLAIN, 15));
        feeField.setBounds(200, 200, 300, 30);
        formPanel.add(feeField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(50, 400, 800, 50);

        JButton submitButton = new JButton("Submit Schedule");
        submitButton.setBounds(510, 0, 150, 40);
        buttonPanel.add(submitButton);

        submitButton.addActionListener(e -> {
            try {
                Integer departureStationID = (Integer) departureStationComboBox.getSelectedItem();
                Integer arrivalStationID = (Integer) arrivalStationComboBox.getSelectedItem();
                if (departureStationID.equals(arrivalStationID)) {
                    JOptionPane.showMessageDialog(null, "Arrival Station cannot be the same as Departure Station!", "Input Error!", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Date departureDateForm = departureDateChooser.getDate();
                Integer selectedTrainID = (Integer) trainList.getSelectedItem();

                if (schController.validateScheduleForm(selectedTrainID, departureStationID, arrivalStationID, departureDateForm, Double.parseDouble(feeField.getText()))) {

                    Schedule newSchedule = new Schedule(selectedTrainID, departureStationID, arrivalStationID, departureDateForm, Double.parseDouble(feeField.getText()));
                    // Update
                    if (schedule.getScheduleID() != null) {
                        newSchedule.addScheduleID(schedule.getScheduleID());
                        if (schController.updateSchedule(newSchedule)) {
                            JOptionPane.showMessageDialog(null, "Schedule Edited Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            new AdminMenu();
                            this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to Edit Schedule!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        if (schController.addNewSchedule(newSchedule)) {
                            JOptionPane.showMessageDialog(null, "New Schedule Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            new AdminMenu();
                            this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to Add Schedule!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "All Fields Must Be Filled!", "Input Error!", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Station ID!", "Input Error!", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(110, 0, 150, 40);
        buttonPanel.add(exitButton);

        exitButton.addActionListener(e -> {
            new AdminMenu();
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

    private void loadStationList(JComboBox<Integer> comboBox, Integer arrivalID) {
        comboBox.removeAllItems();
        ArrayList<Station> stations = (ArrayList<Station>) stationController.getlistStations();
        for (Station station : stations) {
            if (!Objects.equals(station.getId(), arrivalID)) {
                comboBox.addItem(station.getId());
            }
        }
    }

    private void loadTrainList(Integer departureStationId) {
        trainList.removeAllItems();
        ArrayList<Train> trains = stationController.getStationById(departureStationId).getTrainList();
        for (Train train : trains) {
            trainList.addItem(train.getId());
        }
    }
}
