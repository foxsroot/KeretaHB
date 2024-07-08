package view.admin.train;

import controller.TrainController;
import model.classes.Carriage;
import model.classes.Train;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AssignCarriageScreen extends JFrame {
    private final TrainController trainController = new TrainController();

    public AssignCarriageScreen(Train train) {
        this.setTitle("Assign Carriages");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBounds(20, 70, 850, 300);

        // Add search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JTextField showTrainByIdField = new JTextField();
        showTrainByIdField.setFont(new Font("Calibri", Font.BOLD, 16));
        showTrainByIdField.setPreferredSize(new Dimension(600, 30));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Calibri", Font.BOLD, 16));
        searchButton.setPreferredSize(new Dimension(100, 29));

        searchButton.addActionListener(e -> {
            String searchID = showTrainByIdField.getText();
            if (searchID.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill the Train ID", "Empty Field", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    Train searchedTrain = trainController.getTrainById(Integer.parseInt(searchID));
                    if (searchedTrain == null) {
                        JOptionPane.showMessageDialog(null, "Train ID not found", "Schedule Not Found", JOptionPane.ERROR_MESSAGE);
                    } else {
                        AssignCarriageScreen assignCarriageScreen = new AssignCarriageScreen(searchedTrain);
                        this.dispose();
                        assignCarriageScreen.setVisible(true);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Train ID", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        searchPanel.add(showTrainByIdField);
        searchPanel.add(searchButton);

        mainPanel.add(searchPanel);

        displayAssignedCarriages(mainPanel, train);
        displayUnassignedCarriages(mainPanel);

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        this.add(mainScrollPane);
        JScrollBar verticalScrollBar = mainScrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setBlockIncrement(75);
    }

    private void displayAssignedCarriages(JPanel panel, Train train) {
        JLabel label = createLabel("Assigned Carriages");
        JTable table = createTable();
        DefaultTableModel model = createTableModel();
        table.setModel(model);
        populateAssignedCarriages(model, train);

        panel.add(label);
        panel.add(new JScrollPane(table));
    }

    private void displayUnassignedCarriages(JPanel panel) {
        JLabel label = createLabel("Unassigned Carriages");
        JTable table = createTable();
        DefaultTableModel model = createTableModel();
        table.setModel(model);
        populateUnassignedCarriages(model);

        panel.add(label);
        panel.add(new JScrollPane(table));
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    private JTable createTable() {
        JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(850, 100));
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Calibri", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 14));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        return table;
    }

    private DefaultTableModel createTableModel() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] columns = {"Carriage ID", "Train ID", "Carriage Type", "Class Type", "Capacity", "Baggage Allowance"};
        for (String column : columns) {
            model.addColumn(column);
        }
        return model;
    }

    private void populateAssignedCarriages(DefaultTableModel model, Train train) {
        Carriage[] carriages = train.getCarriages();
        for (Carriage carriage : carriages) {
            model.addRow(new Object[]{
                    carriage.getId(),
                    carriage.getTrain_id(),
                    carriage.getType(),
                    carriage.getType(),
                    carriage.getCapacity(),
                    carriage.getBaggageAllowance()
            });
        }
    }

    private void populateUnassignedCarriages(DefaultTableModel model) {
        Carriage[] carriages = trainController.getUnassignedCarriages();
        for (Carriage carriage : carriages) {
            model.addRow(new Object[]{
                    carriage.getId(),
                    carriage.getTrain_id(),
                    carriage.getType(),
                    carriage.getType(),
                    carriage.getCapacity(),
                    carriage.getBaggageAllowance()
            });
        }
    }

    // Testing
}
