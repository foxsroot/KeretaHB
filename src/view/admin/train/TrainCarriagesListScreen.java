package view.admin.train;

import model.classes.Train;
import model.classes.Carriage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TrainCarriagesListScreen extends JFrame {

    public TrainCarriagesListScreen(Train train) {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Carriages List");

        JLabel screenTitle = new JLabel("Train ID: " + train.getId() + " Carriages List");
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 20));
        screenTitle.setBounds(350, 10, 230, 30);
        add(screenTitle);

        JPanel carriageListPanel = new JPanel();
        carriageListPanel.setLayout(null);
        carriageListPanel.setBounds(30, 60, 880, 550);

        List<Carriage> carriages = List.of(train.getCarriages());

        int yOffset = 10;
        int xOffset = 10;
        int counter = 0;

        for (Carriage carriage : carriages) {
            if (counter != 0 && counter % 2 == 0) {
                xOffset = 10;
                yOffset += 160;
            }

            carriageListPanel.add(createCarriagePanel(carriage, xOffset, yOffset));
            counter++;
            xOffset += 410;
        }

        carriageListPanel.setPreferredSize(new Dimension(800, yOffset + 160));

        JScrollPane scrollPane = new JScrollPane(carriageListPanel);
        scrollPane.setBounds(20, 50, 850, 530);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton backButton = new JButton("Back to Train List");
        backButton.setBounds(20, 600, 150, 30);
        backButton.addActionListener(e -> {
            TrainListScreen trainListScreen = new TrainListScreen();
            this.dispose();
            trainListScreen.setVisible(true);
        });

        add(screenTitle);
        add(scrollPane);
        add(backButton);

        this.setVisible(true);
    }

    private JPanel createCarriagePanel(Carriage carriage, int xOffset, int yOffset) {
        JPanel carriagePanel = new JPanel();
        carriagePanel.setLayout(null);
        carriagePanel.setBackground(Color.WHITE);
        carriagePanel.setBounds(xOffset, yOffset, 400, 150);

        JLabel carriageIdLabel = new JLabel("Carriage ID: " + carriage.getId());
        carriageIdLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        carriageIdLabel.setBounds(10, 10, 200, 30);
        carriagePanel.add(carriageIdLabel);

        JLabel carriageTypeLabel = new JLabel("Type: " + carriage.getType());
        carriageTypeLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        carriageTypeLabel.setBounds(10, 40, 200, 30);
        carriagePanel.add(carriageTypeLabel);

        JLabel classTypeLabel = new JLabel("Class: " + carriage.getType());
        classTypeLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        classTypeLabel.setBounds(10, 70, 200, 30);
        carriagePanel.add(classTypeLabel);

        JLabel capacityLabel = new JLabel("Capacity: " + carriage.getCapacity());
        capacityLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        capacityLabel.setBounds(10, 100, 200, 30);
        carriagePanel.add(capacityLabel);

        JLabel baggageLabel = new JLabel("Baggage: " + carriage.getBaggageAllowance());
        baggageLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        baggageLabel.setBounds(220, 40, 200, 30);
        carriagePanel.add(baggageLabel);

        JButton detailButton = new JButton("View Details");
        detailButton.setBounds(220, 90, 130, 30);
        detailButton.addActionListener(e -> {
            // Implement view details action here
        });
        carriagePanel.add(detailButton);

        return carriagePanel;
    }
}
