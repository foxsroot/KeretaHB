package view.admin.schedule;

import com.toedter.calendar.JDateChooser;
import controller.ScheduleController;
import controller.StationController;
import controller.TrainController;
import model.classes.Schedule;
import model.classes.Train;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;

public class AddScheduleScreen extends JFrame {
    private final StationController stationController = new StationController();
    private final ScheduleController schController = new ScheduleController();
    private final JComboBox<Integer> trainList = new JComboBox<>();

    public AddScheduleScreen(Schedule schedule) {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Add Schedule");

        JLabel screenTitle = new JLabel("Add Schedule Form");
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 20));
        screenTitle.setBounds(370, 10, 200, 30);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(20, 70, 850, 300);

        JLabel departureStation = new JLabel("Departure Station ID");
        departureStation.setFont(new Font("Calibri", Font.PLAIN, 17));
        departureStation.setBounds(0, 0, 150, 30);
        formPanel.add(departureStation);

        JTextField departureStationField = new JTextField(schedule.getDeparture() != null ? schedule.getDeparture().toString() : "");
        departureStationField.setFont(new Font("Calibri", Font.PLAIN, 15));
        departureStationField.setBounds(200, 0, 300, 30);
        formPanel.add(departureStationField);

        departureStationField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                loadTrainList(Integer.parseInt(departureStationField.getText()));
            }

        });

        JLabel arrivalStation = new JLabel("Arrival Station ID");
        arrivalStation.setFont(new Font("Calibri", Font.PLAIN, 17));
        arrivalStation.setBounds(0, 50, 130, 30);
        formPanel.add(arrivalStation);

        JTextField arrivalStationField = new JTextField(schedule.getArrival() != null ? schedule.getArrival().toString() : "");
        arrivalStationField.setFont(new Font("Calibri", Font.PLAIN, 15));
        arrivalStationField.setBounds(200, 50, 300, 30);
        formPanel.add(arrivalStationField);

        JLabel departureDate = new JLabel("Departure Date");
        departureDate.setFont(new Font("Calibri", Font.PLAIN, 17));
        departureDate.setBounds(0, 100, 130, 30);
        formPanel.add(departureDate);

        JDateChooser departureDateChooser = new JDateChooser();
        departureDateChooser.setFont(new Font("Calibri", Font.PLAIN, 17));
        departureDateChooser.setBounds(200, 100, 300, 30);
        formPanel.add(departureDateChooser);

        JLabel trainChooser = new JLabel("Choose Train");
        trainChooser.setFont(new Font("Calibri", Font.PLAIN, 17));
        trainChooser.setBounds(0, 150, 130, 30);
        formPanel.add(trainChooser);


        trainList.setBounds(200, 150, 300, 30);
        formPanel.add(trainList);

        JLabel fee = new JLabel("Set Fee");
        fee.setFont(new Font("Calibri", Font.PLAIN, 17));
        fee.setBounds(0, 200, 130, 30);
        formPanel.add(fee);

        JTextField feeField = new JTextField(String.valueOf(schedule.getFee() < 0 ? schedule.getFee() : ""));
        feeField.setFont(new Font("Calibri", Font.PLAIN, 15));
        feeField.setBounds(200, 200, 300, 30);
        formPanel.add(feeField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(50, 400, 800, 50);

        JButton addButton = new JButton("Add Schedule");
        addButton.setBounds(510, 0, 150, 40);
        buttonPanel.add(addButton);

        addButton.addActionListener(e -> {
            try {
                Integer departureStationID = Integer.parseInt(departureStationField.getText());
                Integer arrivalStationID = Integer.parseInt(arrivalStationField.getText());
                Date departureDateForm = departureDateChooser.getDate();
                Train selectedTrain = (Train) trainList.getSelectedItem();
                Integer selectedTrainID = selectedTrain.getId();

                if (schController.validateScheduleForm(selectedTrainID, departureStationID, arrivalStationID, departureDateForm, Double.parseDouble(feeField.getText()))) {
                    Schedule newSchedule = new Schedule(selectedTrainID, departureStationID, arrivalStationID, departureDateForm, Double.parseDouble(feeField.getText()));
                    schController.addSchedule(newSchedule);
                    JOptionPane.showMessageDialog(null, "Schedule Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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

        exitButton.addActionListener(e -> dispose());

        JLabel warningLabel = new JLabel("*Note: All fields must be filled!");
        warningLabel.setFont(new Font("Calibri", Font.BOLD, 13));
        warningLabel.setForeground(new Color(255, 0, 10));
        warningLabel.setBounds(50, 500, 170, 30);

        add(screenTitle);
        add(formPanel);
        add(buttonPanel);
        add(warningLabel);

    }

    private void loadTrainList(Integer departureStationId) {
        trainList.removeAllItems();
        ArrayList<Train> trains = stationController.getStationById(departureStationId).getTrainList();
        for (Train train : trains) {
            trainList.addItem(train.getId());
        }
    }
}
