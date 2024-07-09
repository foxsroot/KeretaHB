package view.admin.train;

import controller.StationController;
import controller.TrainController;
import model.classes.Train;
import view.admin.AdminMenu;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TrainListScreen extends JFrame {
    private final TrainController trainController = new TrainController();

    public TrainListScreen() {
        this.setTitle("Train List");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.setLayout(null);

        JLabel screenTitle = new JLabel("Train List");
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        screenTitle.setBounds(300, 20, 300, 30);
        add(screenTitle);

        JPanel trainListPanel = new JPanel();
        trainListPanel.setLayout(null);
        trainListPanel.setBackground(Color.WHITE);

        displayTrainList(trainListPanel);

        JScrollPane scrollPane = new JScrollPane(trainListPanel);
        scrollPane.setBounds(10, 70, 870, 530);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setBounds(30, 630, 250, 30);
        backButton.setFont(new Font("Calibri", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            AdminMenu admin = new AdminMenu();
        });
        add(backButton);
        this.setVisible(true);
    }

    private void displayTrainList(JPanel panel) {
        List<Train> trains = trainController.getTrainList();
        int yOffset = 10;
        for (Train train : trains) {
            panel.add(createTrainPanel(train, yOffset));
            yOffset += 110;
        }
        panel.setPreferredSize(new Dimension(870, yOffset + 120));
    }

    private JPanel createTrainPanel(Train train, int yOffset) {
        JPanel trainPanel = new JPanel();
        trainPanel.setLayout(null);
        trainPanel.setBounds(10, yOffset, 850, 100);
        trainPanel.setBackground(Color.WHITE);

        JLabel trainId = new JLabel("ID: " + train.getId());
        trainId.setFont(new Font("Calibri", Font.BOLD, 25));
        trainId.setBounds(10, 10, 100, 30);
        trainPanel.add(trainId);

        StationController stationController = new StationController();
        JLabel stationId = new JLabel("Station: " + stationController.getStationNameById(train.getStationId()));
        stationId.setFont(new Font("Calibri", Font.PLAIN, 25));
        stationId.setBounds(120, 10, 300, 30);
        trainPanel.add(stationId);

        JLabel speed = new JLabel("Speed: " + train.getSpeed());
        speed.setFont(new Font("Calibri", Font.PLAIN, 25));
        speed.setBounds(120, 50, 300, 30);
        trainPanel.add(speed);

        JButton viewCarriagesButton = new JButton("View Carriages");
        viewCarriagesButton.setBounds(590, 30, 130, 30);
        viewCarriagesButton.addActionListener(e -> {
            TrainCarriagesListScreen trainCarriageListScreen = new TrainCarriagesListScreen(train);
            this.dispose();
            trainCarriageListScreen.setVisible(true);
        });
        trainPanel.add(viewCarriagesButton);

        JButton viewDetailButton = new JButton("View Detail");
        viewDetailButton.setBounds(730, 30, 110, 30);
        viewDetailButton.addActionListener(e -> {
            TrainDetailScreen trainDetailScreen = new TrainDetailScreen(train);
            this.dispose();
            trainDetailScreen.setVisible(true);
        });
        trainPanel.add(viewDetailButton);

        return trainPanel;
    }

    // Testing
    public static void main(String[] args) {
        TrainListScreen trainListScreen = new TrainListScreen();
        trainListScreen.setVisible(true);
    }
}
