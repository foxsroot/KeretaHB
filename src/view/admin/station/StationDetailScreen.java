package view.admin.station;

import model.classes.Station;
import model.classes.Victual;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class StationDetailScreen extends JFrame {

    public StationDetailScreen(Station station) {
        this.setTitle("Station Detail");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2, 5, 10));
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

        mainPanel.add(createLabel("Victual List"));
        HashMap<Victual, Integer> victuals = station.getVictual();
        if (!victuals.isEmpty()) {
            for (Victual victual : victuals.keySet()) {
                Integer stock = victuals.get(victual);
                mainPanel.add(createLabel(": " + victual.getName() + ", Stock: " + stock));
                mainPanel.add(createLabel(" "));
            }
        } else {
            mainPanel.add(createLabel(": No victuals found"));
            mainPanel.add(createLabel(" "));
        }

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);

        JButton backButton = new JButton("Back to Station List");
        backButton.setFont(new Font("Calibri", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(200, 29));
        backButton.addActionListener(e -> {
            StationListScreen stationListScreen = new StationListScreen();
            this.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        buttonPanel.add(backButton);

        this.setLayout(new BorderLayout());
        this.add(mainScrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }
}
