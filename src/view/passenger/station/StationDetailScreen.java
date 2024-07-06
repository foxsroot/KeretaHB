package view.passenger.station;

import controller.StationController;
import model.classes.Station;
import model.classes.Train;
import model.classes.Victual;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class StationDetailScreen extends JFrame {

    private final StationController stationController = new StationController();
    private ArrayList<Train> trainList = new ArrayList<>();

    public StationDetailScreen(Station station) {
        this.setTitle("Station Detail");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(10, 2, 5, 10));
        mainPanel.setBounds(20, 70, 850, 300);

        mainPanel.add(createLabel("Station ID"));
        mainPanel.add(createLabel(": " + station.getId().toString()));

        mainPanel.add(createLabel("Station Name"));
        mainPanel.add(createLabel(": " + station.getName()));

        mainPanel.add(createLabel("Location"));
        mainPanel.add(createLabel(": " + station.getLocation()));

        mainPanel.add(createLabel("Income"));
        mainPanel.add(createLabel(": $" + station.getIncome()));

        mainPanel.add(createLabel("Number of Trains"));
        mainPanel.add(createLabel(": " + station.getTrainList().size()));

        mainPanel.add(createLabel("Number of Schedules"));
        mainPanel.add(createLabel(": " + station.getSchedules().size()));

        mainPanel.add(createLabel("Victuals"));
        mainPanel.add(createLabel(": " + getVictualDetails(station.getVictual())));

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);

        JButton backButton = new JButton("Back to Station List");
        backButton.setFont(new Font("Calibri", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(200, 29));
        backButton.addActionListener(e -> {
            StationListScreen stationListScreen = new StationListScreen();
            this.dispose();
            stationListScreen.setVisible(true);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        buttonPanel.add(backButton);

        this.setLayout(new BorderLayout());
        this.add(mainScrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    private String getVictualDetails(HashMap<Victual, Integer> victuals) {
        StringBuilder victualDetails = new StringBuilder();
        for (Victual victual : victuals.keySet()) {
            Integer stock = victuals.get(victual);
            victualDetails.append(victual.getName())
                    .append(" (Stock: ")
                    .append(stock)
                    .append("), ");
        }
        if (!victualDetails.isEmpty()) {
            victualDetails.setLength(victualDetails.length() - 2);
        }
        return victualDetails.toString();
    }


}
