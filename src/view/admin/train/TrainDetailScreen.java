package view.admin.train;

import controller.StationController;
import controller.TrainController;
import model.classes.Train;
import view.admin.AdminMenu;

import javax.swing.*;
import java.awt.*;

public class TrainDetailScreen extends JFrame {
    private final TrainController trainController = new TrainController();

    public TrainDetailScreen(Train train) {
        this.setTitle("Train Detail");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        addTrainDetails(mainPanel, train);
        addButtons(mainPanel, train);

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.setLayout(new BorderLayout());
        this.add(mainScrollPane, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void addTrainDetails(JPanel panel, Train train) {
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new GridLayout(5, 2, 10, 10));
        detailPanel.setBackground(Color.WHITE);

        StationController stController = new StationController();

        detailPanel.add(createLabel("Train ID"));
        detailPanel.add(createValueLabel(String.valueOf(train.getId())));

        detailPanel.add(createLabel("Train Speed"));
        detailPanel.add(createValueLabel(train.getSpeed() + " km/h"));

        int capacity = trainController.totalTrainCapacity(train.getId());
        detailPanel.add(createLabel("Seat Capacity"));
        detailPanel.add(createValueLabel(String.valueOf(capacity)));

        String station = stController.getStationNameById(train.getStationId());
        detailPanel.add(createLabel("Departure Station"));
        detailPanel.add(createValueLabel(station));

        panel.add(detailPanel);
    }

    private void addButtons(JPanel panel, Train train) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        JButton editTrainButton = createButton("Edit Train");
        editTrainButton.addActionListener(e -> {
            AddEditTrainScreen editTrainScreen = new AddEditTrainScreen(train);
            this.dispose();
            editTrainScreen.setVisible(true);
        });

        JButton editCarriageButton = createButton("Edit Carriages");
        editCarriageButton.addActionListener(e -> {
            new AssignRevokeCarriageScreen(train);
            this.dispose();
        });

        JButton deleteTrainButton = createButton("Delete Train");
        deleteTrainButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this Train?", "Warning", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (trainController.deleteTrain(train)) {
                    JOptionPane.showMessageDialog(null, "Train deleted successfully", "Delete Train", JOptionPane.INFORMATION_MESSAGE);
                    AdminMenu adminMenu = new AdminMenu();
                    this.dispose();
                    adminMenu.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete train", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backButton = createButton("Back to Train Selection");
        backButton.setFont(new Font("Calibri", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(200, 30));
        backButton.addActionListener(e -> {
            TrainListScreen selectTrain = new TrainListScreen();
            this.dispose();
            selectTrain.setVisible(true);
        });

        buttonPanel.add(backButton);
        buttonPanel.add(editTrainButton);
        buttonPanel.add(editCarriageButton);
        buttonPanel.add(deleteTrainButton);

        panel.add(buttonPanel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.PLAIN, 18));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Calibri", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(150, 30));
        return button;
    }
}
