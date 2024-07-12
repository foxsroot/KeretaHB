package view.admin.profile;

import controller.UserController;
import model.classes.Admin;

import javax.swing.*;
import java.awt.*;

public class EditProfile extends JFrame {
	public EditProfile(Admin admin) {
		this.setVisible(true);
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

		JTextField usernameField = new JTextField(admin.getName());
		usernameField.setFont(new Font("calibri", Font.PLAIN, 15));
		usernameField.setBounds(100, 40, 200, 20);
		formProfile.add(usernameField);

		JTextField emailField = new JTextField(admin.getEmail());
		emailField.setFont(new Font("calibri", Font.PLAIN, 15));
		emailField.setBounds(100, 90, 200, 20);
		formProfile.add(emailField);

		JButton submit = new JButton("Submit");
		submit.setBounds(0, 120, 100, 20);
		formProfile.add(submit);

		JButton cancel = new JButton("Cancel");
		cancel.setBounds(150, 120, 100, 20);
		formProfile.add(cancel);

		submit.addActionListener(e -> {
			String username = usernameField.getText();
			String email = emailField.getText();
			if (!new UserController().updateProfile(admin.getId(), "admin", new String[]{username, email})) {
				JOptionPane.showMessageDialog(null, "Gagal mengganti profile, silahkan input ulang.");
			} else {
				JOptionPane.showMessageDialog(null, "Berhasil update profile!");
				this.dispose();
				new Profile(admin.getId());
			}
		});

		cancel.addActionListener(e -> {
			this.dispose();
			new Profile(admin.getId());
		});

		add(formProfile);
	}
}
