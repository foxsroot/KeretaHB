package view.admin.victual;

import controller.ImageController;
import controller.VictualController;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.File;
import java.text.NumberFormat;

public class AddVictualScreen extends JFrame {
    File imageFile;

    public AddVictualScreen() {
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setSize(900, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Add Victual");

        JLabel screenTitle = new JLabel("Add Victual Form");
        screenTitle.setFont(new Font("calibri", Font.BOLD, 20));
        screenTitle.setBounds(360, 10, 200, 30);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(30, 60, 880, 550);

        JLabel victualName = new JLabel("Victual Name             :");
        victualName.setFont(new Font("calibri", Font.BOLD, 20));
        victualName.setBounds(0, 10, 200, 30);
        formPanel.add(victualName);

        JTextField victualNameField = new JTextField();
        victualNameField.setFont(new Font("calibri", Font.PLAIN, 18));
        victualNameField.setBounds(210, 10, 300, 30);
        formPanel.add(victualNameField);

        JLabel victualPrice = new JLabel("Victual Price               :");
        victualPrice.setFont(new Font("calibri", Font.BOLD, 20));
        victualPrice.setBounds(0, 60, 200, 30);
        formPanel.add(victualPrice);

        NumberFormat format = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(1);
        numberFormatter.setMaximum(999999999);

        JFormattedTextField victualPriceField = new JFormattedTextField(numberFormatter);
        victualPriceField.setFont(new Font("calibri", Font.PLAIN, 18));
        victualPriceField.setBounds(210, 60, 300, 30);
        victualPriceField.setColumns(9);

        formPanel.add(victualPriceField);

        JLabel victualDescription = new JLabel("Victual Description    :");
        victualDescription.setFont(new Font("calibri", Font.BOLD, 20));
        victualDescription.setBounds(0, 110, 200, 30);
        formPanel.add(victualDescription);

        JTextArea victualDescriptionArea = new JTextArea();
        victualDescriptionArea.setFont(new Font("calibri", Font.PLAIN, 18));
        victualDescriptionArea.setBounds(210, 110, 500, 150);
        victualDescriptionArea.setLineWrap(true);
        formPanel.add(victualDescriptionArea);

        JLabel victualImage = new JLabel("Victual Image              :");
        victualImage.setFont(new Font("calibri", Font.BOLD, 20));
        victualImage.setBounds(0, 300, 200, 30);
        formPanel.add(victualImage);

        JLabel image = new JLabel();
        image.setBounds(210, 300, 180, 240);
        image.setVisible(false);
        formPanel.add(image);

        JButton chooseImageButton = new JButton("Choose Image");
        chooseImageButton.setBounds(210, 300, 120, 25);
        formPanel.add(chooseImageButton);

        chooseImageButton.addActionListener(e -> {
            FileDialog fd = new FileDialog(new JFrame(), "Choose victual image", FileDialog.LOAD);
            fd.setDirectory("C:\\");
            fd.setVisible(true);

            if (fd.getFile() != null) {
                String filePath = fd.getDirectory() + fd.getFile();
                System.out.println(filePath);
                imageFile = new File(filePath);
                image.setIcon(new ImageIcon(ImageController.resizeImage(filePath, 180, 240)));
                chooseImageButton.setBounds(420, 400, 120, 25);
                chooseImageButton.setText("Replace Image");
                image.setVisible(true);

                fd.dispose();
            }
        });

        JButton insertButton = new JButton("Insert");
        insertButton.setBounds(630, 620, 100, 30);

        insertButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Insert This Item?", "Insert Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                VictualController controller = new VictualController();
                if (controller.verifyForm(victualNameField.getText(), victualPriceField.getText(), victualDescriptionArea.getText(), imageFile)) {
                    if (controller.addVictual(victualNameField.getText(), victualPriceField.getText(), victualDescriptionArea.getText(), imageFile)) {
                        JOptionPane.showMessageDialog(null, "Victual Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        System.out.println("Gagal masukin :(");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "All fields must be filled!", "Input Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(20, 620, 150, 30);
        exitButton.addActionListener(e -> {
            dispose();
            //balik ke main menu
        });

        add(screenTitle);
        add(formPanel);
        add(exitButton);
        add(insertButton);
    }

    public static void main(String[] args) {
        new AddVictualScreen();
    }
}
