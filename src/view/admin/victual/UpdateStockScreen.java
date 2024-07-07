package view.admin.victual;

import config.DirectoryConfig;
import controller.ImageController;
import controller.VictualController;
import model.classes.Station;
import model.classes.Victual;
import view.passenger.victual.ListStationScreen;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;

public class UpdateStockScreen extends JFrame {
    Station station;
    JPanel victualPanel;

    public UpdateStockScreen(Station station) {
        this.station = station;
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Victual Stock");

        JLabel screenTitle = new JLabel("Victual Stock");
        screenTitle.setFont(new Font("calibri", Font.BOLD, 20));
        screenTitle.setBounds(400, 10, 200, 30);

        victualPanel = new JPanel();
        victualPanel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane(victualPanel);

        insertVictualPanel();

        scrollPane.setBounds(10, 80, 870, 520);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton exitButton = new JButton("Back to List Station");
        exitButton.setBounds(10, 610, 200, 40);

        exitButton.addActionListener(e -> {
           dispose();
           new ListStationScreen();
        });

        add(scrollPane);
        add(screenTitle);
        add(exitButton);
    }

    private void insertVictualPanel() {
        int counter = 0;
        int xOffset = 10;
        int yOffset = 10;

        VictualController victualController = new VictualController();
        ArrayList<Victual> victuals = (ArrayList<Victual>) victualController.getVictualList();

        for (Victual victual : victuals) {
            if (counter != 0 && counter % 2 == 0) {
                xOffset = 10;
                yOffset += 210;
            }

            JPanel victualBox = createVictualPanel(victual, xOffset, yOffset);
            victualPanel.add(victualBox);

            xOffset += 410;
            counter++;
        }

        victualPanel.setPreferredSize(new Dimension(880, yOffset + 210));

        revalidate();
        repaint();
    }

    private JPanel createVictualPanel(Victual victual, int xOffset, int yOffset) {
        JPanel singleVictualPanel = new JPanel();
        singleVictualPanel.setLayout(null);
        singleVictualPanel.setBackground(Color.WHITE);
        singleVictualPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        singleVictualPanel.setBounds(xOffset, yOffset, 400, 200);

        JLabel victualImage = new JLabel();
        victualImage.setBounds(10, 10, 150, 180);
        victualImage.setIcon(new ImageIcon(ImageController.resizeImage(DirectoryConfig.VICTUAL_IMAGES + victual.getImage(), 150, 180)));
        singleVictualPanel.add(victualImage);

        JLabel victualName = new JLabel(victual.getName());
        victualName.setFont(new Font("calibri", Font.BOLD, 20));
        victualName.setBounds(180, 10, 180, 25);
        singleVictualPanel.add(victualName);

        JLabel victualPrice = new JLabel("Rp " + victual.getPrice());
        victualPrice.setFont(new Font("calibri", Font.PLAIN, 18));
        victualPrice.setBounds(180, 40, 180, 20);
        singleVictualPanel.add(victualPrice);

        VictualController victualController = new VictualController();
        int stock = victualController.getStock(victual.getId(), station.getId());

        if (stock == -1) {
            JButton insertButton = new JButton("Add Item To Station");
            insertButton.setBounds(180, 70, 150, 40);

            insertButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(null, "Insert this item to the station?", "Insert Item Confirmation", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    int initStock = 0;
                    boolean valid = false;
                    String initialStock = JOptionPane.showInputDialog(null, "Initial Stock", 1);

                    try {
                        initStock = Integer.parseInt(initialStock);
                        valid = true;
                    } catch (NumberFormatException err) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid stock (Number only)", "Error Input", JOptionPane.ERROR_MESSAGE);
                    }

                    if (valid && victualController.insertNewStock(victual.getId(), station.getId(), initStock)) {
                        JOptionPane.showMessageDialog(null, "Successfully added item to the station", "Success", JOptionPane.INFORMATION_MESSAGE);
                        victualPanel.removeAll();
                        insertVictualPanel();
                    }
                }
            });
            singleVictualPanel.add(insertButton);
        } else {
            JLabel stockLabel = new JLabel("Current Stock: x" + stock);
            stockLabel.setFont(new Font("calibri", Font.PLAIN, 18));
            stockLabel.setBounds(180, 70, 150, 20);
            singleVictualPanel.add(stockLabel);

            NumberFormat format = NumberFormat.getIntegerInstance();
            NumberFormatter numberFormatter = new NumberFormatter(format);
            numberFormatter.setValueClass(Integer.class);
            numberFormatter.setAllowsInvalid(false);
            numberFormatter.setMinimum(1);
            numberFormatter.setMaximum(999999999);

            JFormattedTextField quantityField = new JFormattedTextField(numberFormatter);
            quantityField.setFont(new Font("calibri", Font.PLAIN, 18));
            quantityField.setBounds(180, 100, 110, 30);
            quantityField.setColumns(9);
            quantityField.setText(String.valueOf(victualController.getStock(victual.getId(), station.getId())));
            singleVictualPanel.add(quantityField);

            JButton increaseButton = new JButton("+");
            increaseButton.setBounds(340, 100, 45, 30);

            increaseButton.addActionListener(e -> {
                int currentStock = Integer.parseInt(quantityField.getText());
                quantityField.setText(String.valueOf(currentStock + 1));
            });
            singleVictualPanel.add(increaseButton);

            JButton decreaseButton = new JButton("-");
            decreaseButton.setBounds(292, 100, 45, 30);

            decreaseButton.addActionListener(e -> {
                int currentStock = Integer.parseInt(quantityField.getText());
                if (currentStock > 0) {
                    quantityField.setText(String.valueOf(currentStock - 1));
                }
            });
            singleVictualPanel.add(decreaseButton);

            JButton updateButton = new JButton("Update Stock");
            updateButton.setBounds(180, 140, 110, 30);

            updateButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(null, "Update This Item Stock?", "Update Stock Warning", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        int newStock = Integer.parseInt(quantityField.getText());
                        if (newStock >= 0 && victualController.updateStock(victual.getId(), station.getId(), newStock)) {
                            JOptionPane.showMessageDialog(null, "Stock updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to update stock", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException err) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            singleVictualPanel.add(updateButton);

            JButton removeButton = new JButton("Remove");
            removeButton.setBackground(Color.RED);
            removeButton.setForeground(Color.WHITE);
            removeButton.setBounds(295, 140, 90, 30);

            removeButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(null, "Remove This Item From This Station?", "Remove From Station Confirmation", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION && victualController.removeFromStation(victual.getId(), station.getId())) {
                    JOptionPane.showMessageDialog(null, "Item Removed From Station!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    victualPanel.removeAll();
                    insertVictualPanel();
                }
            });
            singleVictualPanel.add(removeButton);
        }

        return singleVictualPanel;
    }
}
