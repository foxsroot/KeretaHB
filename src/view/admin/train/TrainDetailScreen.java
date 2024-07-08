package view.admin.train;

import controller.ScheduleController;
import controller.StationController;
import controller.TrainController;
import model.classes.Carriage;
import model.classes.Schedule;
import model.classes.Train;
import view.admin.AdminMenu;
import view.admin.schedule.AddEditScheduleScreen;
import view.admin.schedule.StationScheduleSelection;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class TrainDetailScreen extends JFrame {
    public TrainDetailScreen(Train train) {
        this.setTitle("Train Detail");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        StationController stController = new StationController();
        TrainController trainController = new TrainController();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(10, 2, 5, 10));
        mainPanel.setBounds(20, 70, 850, 300);

        mainPanel.add(createLabel("Train ID"));
        mainPanel.add(createLabel(": " + train.getId()));

        mainPanel.add(createLabel("Train Speed:"));
        mainPanel.add(createLabel(": " + train.getSpeed()));

        int capacity = trainController.totalTrainCapacity(train.getId());
        mainPanel.add(createLabel("Seat Capacity"));
        mainPanel.add(createLabel(": " + capacity));

        String station = stController.getStationNameById(train.getStationId());
        mainPanel.add(createLabel("Departure Station"));
        mainPanel.add(createLabel(": " + station));

        mainPanel.add(createLabel("Carriage List"));
        Carriage[] carriageList = train.getCarriages();
        if (carriageList[0] != null) {
            for (int i = 0; i < carriageList.length; i++) {
                mainPanel.add(createLabel(": " + carriageList[i].getType() + ", Class: " + carriageList[i].getCarriageClass() + ", Capacity: " + carriageList[i].getCapacity()));
                mainPanel.add(createLabel(" "));
            }
        } else {
            mainPanel.add(createLabel(": No carriages found"));
        }

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);

        JButton editTrainButton = new JButton("Edit Train");
        editTrainButton.setFont(new Font("Calibri", Font.BOLD, 16));
        editTrainButton.setPreferredSize(new Dimension(150, 29));

        JButton editCarriageButton = new JButton("Edit Carriages");
        editCarriageButton.setFont(new Font("Calibri", Font.BOLD, 16));
        editCarriageButton.setPreferredSize(new Dimension(150, 29));

        JButton deleteTrainButton = new JButton("Delete Train");
        deleteTrainButton.setFont(new Font("Calibri", Font.BOLD, 16));
        deleteTrainButton.setPreferredSize(new Dimension(150, 29));


        JButton backButton = new JButton("Back to Schedule Selection");
        backButton.setFont(new Font("Calibri", Font.BOLD, 16));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(editTrainButton);
        buttonPanel.add(editCarriageButton);
        buttonPanel.add(deleteTrainButton);

        this.setLayout(new BorderLayout());
        this.add(mainScrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        editTrainButton.addActionListener(e -> {
            AddEditTrainScreen editTrainScreen = new AddEditTrainScreen(train);
            this.dispose();
            editTrainScreen.setVisible(true);
        });
        editCarriageButton.addActionListener(e -> {
            AssignCarriageScreen assignCarriageScreen = new AssignCarriageScreen(train);
            this.dispose();
            assignCarriageScreen.setVisible(true);
        });
        deleteTrainButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this Train?", "Warning", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (trainController.deleteTrain(train)) {
                    JOptionPane.showMessageDialog(null, "Train deleted successfully", "Delete Train", JOptionPane.INFORMATION_MESSAGE);
                    AdminMenu adminMenu = new AdminMenu();
                    this.dispose();
                    adminMenu.setVisible(true);
                }
            }
        });
        backButton.addActionListener(e -> {
            StationScheduleSelection selectStation = new StationScheduleSelection();
            this.dispose();
            selectStation.setVisible(true);
        });
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }
}
