package view.admin.train;

import controller.AuthenticationHelper;
import controller.CarriageController;
import controller.TrainController;
import model.classes.Carriage;
import model.classes.Train;
import view.guest.Login;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AssignRevokeCarriageScreen extends JFrame {
    private final CarriageController carriageController = new CarriageController();
    private final TrainController trainController = new TrainController();
    private DefaultTableModel assignedModel;
    private DefaultTableModel unassignedModel;
    private Train train;

    public AssignRevokeCarriageScreen(Train train) {
        this.train = train;
        this.setTitle("Assign Carriages");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        JLabel screenTitle = new JLabel("Assign Carriages to Train ID: " + train.getId());
        screenTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        screenTitle.setBounds(250, 20, 400, 30);
        add(screenTitle);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        displayAssignedCarriages(mainPanel);
        displayUnassignedCarriages(mainPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBounds(10, 70, 870, 530);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        JButton backButton = new JButton("Back to Train List");
        backButton.setBounds(30, 630, 250, 30);
        backButton.setFont(new Font("Calibri", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            TrainListScreen trainListScreen = new TrainListScreen();
            this.dispose();
            trainListScreen.setVisible(true);
        });
        add(backButton);

        
        int userId = AuthenticationHelper.getInstance().getUserId();
        if (userId == 0) {
            this.dispose();
            new Login();
        } else {
            this.setVisible(true);
        }
    }

    private void displayAssignedCarriages(JPanel panel) {
        JLabel label = createLabel("Assigned Carriages");
        label.setBounds(10, 10, 300, 30);
        panel.add(label);

        JTable table = createTable();
        assignedModel = createTableModel();
        table.setModel(assignedModel);
        populateAssignedCarriages(assignedModel, train);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 50, 850, 200);
        panel.add(scrollPane);

        JButton removeButton = new JButton("Remove Carriage");
        removeButton.setBounds(650, 10, 200, 30);
        removeButton.setFont(new Font("Calibri", Font.BOLD, 16));
        removeButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int carriageId = (int) assignedModel.getValueAt(selectedRow, 0);
                if (carriageController.revokeCarriageFromTrain(carriageId, train.getId())) {
                    JOptionPane.showMessageDialog(null, "Carriage removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTrainAndTables();
                } else {
                    JOptionPane.showMessageDialog(null, "Error removing carriage!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a carriage to remove!", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        });
        panel.add(removeButton);
    }

    private void displayUnassignedCarriages(JPanel panel) {
        JLabel label = createLabel("Unassigned Carriages");
        label.setBounds(10, 270, 300, 30);
        panel.add(label);

        JTable table = createTable();
        unassignedModel = createTableModel();
        table.setModel(unassignedModel);
        populateUnassignedCarriages(unassignedModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 310, 850, 200);
        panel.add(scrollPane);

        JButton assignButton = new JButton("Assign Carriage");
        assignButton.setBounds(650, 270, 200, 30);
        assignButton.setFont(new Font("Calibri", Font.BOLD, 16));
        assignButton.addActionListener(e -> {
            if (train.getCarriages().length >= 5) {
                JOptionPane.showMessageDialog(null, "This Train Max Carriage limit has been reached", "Max Carriage Reached", JOptionPane.WARNING_MESSAGE);
            } else {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int carriageId = (int) unassignedModel.getValueAt(selectedRow, 0);
                    if (carriageController.assignCarriageToTrain(carriageId, train.getId())) {
                        JOptionPane.showMessageDialog(null, "Carriage assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshTrainAndTables();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error assigning carriage!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a carriage to assign!", "Selection Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        panel.add(assignButton);
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
        List<Carriage> carriages = carriageController.getUnassignedCarriages();
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

    private void refreshTrainAndTables() {
        this.train = trainController.getTrainById(train.getId());
        assignedModel.setRowCount(0);
        unassignedModel.setRowCount(0);
        populateAssignedCarriages(assignedModel, train);
        populateUnassignedCarriages(unassignedModel);
    }

    // Testing
    public static void main(String[] args) {
        TrainController trainController = new TrainController();
        Train sampleTrain = trainController.getTrainById(1);
        AssignRevokeCarriageScreen assignUnassignCarriageScreen = new AssignRevokeCarriageScreen(sampleTrain);
        assignUnassignCarriageScreen.setVisible(true);
    }
}
