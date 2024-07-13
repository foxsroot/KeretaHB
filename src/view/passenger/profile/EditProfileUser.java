package view.passenger.profile;

import controller.AuthenticationHelper;
import controller.UserController;
import model.classes.Passenger;
import view.guest.Login;

import javax.swing.*;
import java.awt.*;

public class EditProfileUser extends JFrame {
	public EditProfileUser(Passenger user) {
        int userId = AuthenticationHelper.getInstance().getUserId();
        if (userId == 0) {
            this.dispose();
            new Login();
        } else {
            this.setVisible(true);
        }
		this.setSize(500, 300);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Changes Your Profile");

		JPanel formProfile = new JPanel();
		formProfile.setLayout(null);
		formProfile.setBounds(50, 10, 400, 250);

		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(0, 0, 200, 100);
		formProfile.add(usernameLabel);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(0, 50, 200, 100);
		formProfile.add(emailLabel);

		JLabel phoneLabel = new JLabel("Phone:");
		phoneLabel.setBounds(0, 100, 200, 100);
		formProfile.add(phoneLabel);

		JTextField usernameField = new JTextField(user.getName());
		usernameField.setFont(new Font("calibri", Font.PLAIN, 15));
		usernameField.setBounds(100, 40, 200, 20);
		formProfile.add(usernameField);

		JTextField emailField = new JTextField(user.getEmail());
		emailField.setFont(new Font("calibri", Font.PLAIN, 15));
		emailField.setBounds(100, 90, 200, 20);
		formProfile.add(emailField);

		JTextField phoneField = new JTextField(user.getCellphone());
		phoneField.setFont(new Font("calibri", Font.PLAIN, 15));
		phoneField.setBounds(100, 140, 200, 20);
		formProfile.add(phoneField);

		JButton submit = new JButton("Submit");
		submit.setBounds(0, 200, 100, 20);
		formProfile.add(submit);

		JButton cancel = new JButton("Cancel");
		cancel.setBounds(150, 200, 100, 20);
		formProfile.add(cancel);

		submit.addActionListener(e -> {
			String username = usernameField.getText();
			String email = emailField.getText();
			String phone = phoneField.getText();
			if (!new UserController().updateProfile(user.getId(), new String[]{username, email, phone})) {
				JOptionPane.showMessageDialog(null, "Gagal mengganti profile.");
			} else {
				JOptionPane.showMessageDialog(null, "Berhasil update profile!");
				this.dispose();
				new ProfileUser(user.getId());
			}
		});

		cancel.addActionListener(e -> {
			this.dispose();
			new ProfileUser(user.getId());
		});

		add(formProfile);
	}
}
