package view.admin.schedule;

import com.toedter.calendar.JDateChooser;
import controller.AuthenticationHelper;
import controller.ScheduleController;
import controller.StationController;
import controller.TrainController;
import model.classes.Schedule;
import model.classes.Station;
import model.classes.Train;
import view.admin.AdminMenu;
import view.guest.Login;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class EditScheduleScreen extends JFrame {
    private final StationController stationController = new StationController();
    private final ScheduleController schController = new ScheduleController();

    public EditScheduleScreen(Schedule schedule) {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Schedule Form");

        JLabel screenTitle = new JLabel("Edit Schedule ID: " + schedule.getScheduleID());
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 20));
        screenTitle.setBounds(370, 10, 200, 30);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(20, 70, 850, 350);

        JLabel departureStation = new JLabel("Departure Station ID");
        departureStation.setFont(new Font("Calibri", Font.PLAIN, 17));
        departureStation.setBounds(0, 0, 150, 30);
        formPanel.add(departureStation);

        JLabel departureStationField = new JLabel(String.valueOf(schedule.getDeparture()));
        departureStationField.setFont(new Font("Calibri", Font.PLAIN, 15));
        departureStationField.setBounds(200, 0, 300, 30);
        formPanel.add(departureStationField);

        JLabel arrivalStation = new JLabel("Arrival Station ID");
        arrivalStation.setFont(new Font("Calibri", Font.PLAIN, 17));
        arrivalStation.setBounds(0, 50, 130, 30);
        formPanel.add(arrivalStation);

        JLabel arrivalStationField = new JLabel(String.valueOf(schedule.getArrival()));
        arrivalStationField.setFont(new Font("Calibri", Font.PLAIN, 15));
        arrivalStationField.setBounds(200, 50, 300, 30);
        formPanel.add(arrivalStationField);


        JLabel departureDateLabel = new JLabel("Departure Date");
        departureDateLabel.setFont(new Font("Calibri", Font.PLAIN, 17));
        departureDateLabel.setBounds(0, 100, 130, 30);
        formPanel.add(departureDateLabel);

        JDateChooser departureDateChooser = new JDateChooser();
        if (schedule.getDepartureDate() != null) {
            departureDateChooser.setDate(new Date(schedule.getDepartureDate().getTime()));
        }
        departureDateChooser.setFont(new Font("Calibri", Font.PLAIN, 17));
        departureDateChooser.setBounds(200, 100, 300, 30);
        formPanel.add(departureDateChooser);

        JLabel departureTimeLabel = new JLabel("Departure Time");
        departureTimeLabel.setFont(new Font("Calibri", Font.PLAIN, 17));
        departureTimeLabel.setBounds(0, 150, 130, 30);
        formPanel.add(departureTimeLabel);

        JSpinner departureTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(departureTimeSpinner, "HH:mm");
        departureTimeSpinner.setEditor(timeEditor);
        departureTimeSpinner.setFont(new Font("Calibri", Font.PLAIN, 17));
        departureTimeSpinner.setBounds(200, 150, 300, 30);
        departureTimeSpinner.setValue(new Date(schedule.getDepartureDate().getTime()));
        formPanel.add(departureTimeSpinner);

        JLabel trainLabel = new JLabel("Train");
        trainLabel.setFont(new Font("Calibri", Font.PLAIN, 17));
        trainLabel.setBounds(0, 200, 130, 30);
        formPanel.add(trainLabel);

        JLabel trainFieldLabel = new JLabel(String.valueOf(schedule.getTrainID()));
        trainFieldLabel.setFont(new Font("Calibri", Font.PLAIN, 17));
        trainFieldLabel.setBounds(200, 200, 300, 30);
        formPanel.add(trainFieldLabel);

        JLabel fee = new JLabel("Set Fee");
        fee.setFont(new Font("Calibri", Font.PLAIN, 17));
        fee.setBounds(0, 250, 130, 30);
        formPanel.add(fee);

        JTextField feeField = new JTextField(String.valueOf(schedule.getFee() != 0 ? schedule.getFee() : ""));
        feeField.setFont(new Font("Calibri", Font.PLAIN, 15));
        feeField.setBounds(200, 250, 300, 30);
        formPanel.add(feeField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(50, 450, 800, 50);

        JButton submitButton = new JButton("Submit Schedule");
        submitButton.setBounds(510, 0, 150, 40);
        buttonPanel.add(submitButton);

        submitButton.addActionListener(e -> {
            try {
                Date departureDate = departureDateChooser.getDate();
                Date departureTime = (Date) departureTimeSpinner.getValue();

                if (departureDate == null || departureTime == null) {
                    JOptionPane.showMessageDialog(null, "Please select a valid date and time.", "Input Error!", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Timestamp departureTimestamp = combineDateAndTime(departureDate, departureTime);
                if (schController.validateScheduleForm(schedule.getTrainID(), schedule.getDeparture(), schedule.getArrival(), departureTimestamp, Double.parseDouble(feeField.getText()))) {
                    Schedule newSchedule = new Schedule(schedule.getTrainID(), schedule.getDeparture(), schedule.getArrival(), departureTimestamp, Double.parseDouble(feeField.getText()));
                    // Update
                    newSchedule.addScheduleID(schedule.getScheduleID());
                    if (schController.updateSchedule(newSchedule)) {
                        JOptionPane.showMessageDialog(null, "Schedule Edited Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        new StationScheduleSelection();
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to Edit Schedule!", "Error", JOptionPane.ERROR_MESSAGE);
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

        
        int userId = AuthenticationHelper.getInstance().getUserId();
        if (userId == 0) {
            this.dispose();
            new Login();
        } else {
            this.setVisible(true);
        }
    }



    private Timestamp combineDateAndTime(Date date, Date time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String dateStr = dateFormat.format(date);
        String timeStr = timeFormat.format(time);
        String dateTimeStr = dateStr + " " + timeStr + ":00";
        return Timestamp.valueOf(dateTimeStr);
    }
}
