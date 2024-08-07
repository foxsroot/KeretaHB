package view.passenger.victual;

import config.DirectoryConfig;
import controller.*;
import model.classes.Station;
import model.classes.Victual;
import view.guest.Login;
import view.passenger.PassengerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class ListVictualScreen extends JFrame {
    HashMap<Victual, Integer> victuals;
    JPanel victualPanel;
    JScrollPane scrollPane;
    VictualController controller = new VictualController();
    int selectedStationId;

    public ListVictualScreen() {
        initComponents();
        int userId = AuthenticationHelper.getInstance().getUserId();
        if (userId == 0) {
            this.dispose();
            new Login();
        } else {
            this.setVisible(true);
        }
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Victual List");

        JLabel screenTitle = new JLabel("Victual List");
        screenTitle.setFont(new Font("calibri", Font.BOLD, 20));
        screenTitle.setBounds(400, 10, 200, 30);

        JLabel stationList = new JLabel("Choose a Station :");
        stationList.setBounds(10, 40, 120, 20);

        StationController stationController = new StationController();
        ArrayList<Station> stations = (ArrayList<Station>) stationController.getlistStations();

        String[] stationNames = new String[stations.size()];

        for (int i = 0; i < stations.size(); i++) {
            stationNames[i] = stations.get(i).getName();
        }

        JComboBox<String> stationSelection = new JComboBox<>(stationNames);
        stationSelection.setBounds(120, 40, 150, 20);

        stationSelection.addActionListener(e -> {
            int selectedIndex = stationSelection.getSelectedIndex();
            Station selectedStation = stations.get(selectedIndex);
            selectedStationId = selectedStation.getId();
            victuals = controller.listVictual(selectedStationId);
            loadVictuals();
        });

        selectedStationId = stations.get(0).getId();
        victuals = controller.listVictual(selectedStationId);
        victualPanel = new JPanel();
        victualPanel.setLayout(null);

        scrollPane = new JScrollPane(victualPanel);
        loadVictuals();

        scrollPane.setBounds(10, 80, 870, 520);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(10, 610, 150, 40);

        exitButton.addActionListener(e -> {
            dispose();
            new PassengerMenu();
        });

        add(stationList);
        add(stationSelection);
        add(scrollPane);
        add(screenTitle);
        add(exitButton);
    }

    private void loadVictuals() {
        victualPanel.removeAll();

        int counter = 0;
        int xOffset = 10;
        int yOffset = 10;

        for (Victual victual : victuals.keySet()) {
            if (counter != 0 && counter % 4 == 0) {
                xOffset = 10;
                yOffset += 310;
            }

            JPanel victualBox = createVictualPanel(victual, xOffset, yOffset);
            victualPanel.add(victualBox);

            xOffset += 210;
            counter++;
        }

        victualPanel.setPreferredSize(new Dimension(880, yOffset + 310));
        victualPanel.revalidate();
        victualPanel.repaint();
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private JPanel createVictualPanel(Victual victual, int xOffset, int yOffset) {
        JPanel victualPanel = new JPanel();
        victualPanel.setLayout(null);
        victualPanel.setBackground(Color.WHITE);
        victualPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        victualPanel.setBounds(xOffset, yOffset, 200, 300);

        JLabel victualImage = new JLabel();
        victualImage.setBounds(10, 10, 180, 240);
        victualImage.setIcon(new ImageIcon(ImageController.resizeImage(DirectoryConfig.VICTUAL_IMAGES + victual.getImage(), 180, 240)));
        victualPanel.add(victualImage);

        JLabel victualName = new JLabel(victual.getName());
        victualName.setFont(new Font("calibri", Font.BOLD, 18));
        victualName.setBounds(10, 253, 180, 25);
        victualPanel.add(victualName);

        JLabel victualPrice = new JLabel("Rp " + victual.getPrice());
        victualPrice.setFont(new Font("calibri", Font.PLAIN, 16));
        victualPrice.setBounds(10, 275, 180, 20);
        victualPanel.add(victualPrice);

        victualPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new ViewVictualScreen(victual, selectedStationId);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                victualPanel.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                victualPanel.setBackground(null);
            }
        });

        return victualPanel;
    }
}
