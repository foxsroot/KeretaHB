package view.passenger.profile;

import controller.UserController;
import model.classes.Passenger;
import view.admin.profile.ProfileUser;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordUser extends JFrame {
	public ChangePasswordUser(Passenger user) {
		this.setVisible(true);
		this.setSize(500, 300);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Changes Your Password");

		JPanel formProfile = new JPanel();
		formProfile.setLayout(null);
		formProfile.setBounds(50, 10, 400, 250);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(0, 0, 200, 100);
		formProfile.add(passwordLabel);

		JLabel newPasswordLabel = new JLabel("New Password:");
		newPasswordLabel.setBounds(0, 50, 200, 100);
		formProfile.add(newPasswordLabel);

		JTextField passwordField = new JTextField(user.getName());
		passwordField.setFont(new Font("calibri", Font.PLAIN, 15));
		passwordField.setBounds(100, 40, 200, 20);
		formProfile.add(passwordField);

		JTextField newPasswordField = new JTextField(user.getEmail());
		newPasswordField.setFont(new Font("calibri", Font.PLAIN, 15));
		newPasswordField.setBounds(100, 90, 200, 20);
		formProfile.add(newPasswordField);

		JButton submit = new JButton("Submit");
		submit.setBounds(0, 120, 100, 20);
		formProfile.add(submit);

		JButton cancel = new JButton("Cancel");
		cancel.setBounds(150, 120, 100, 20);
		formProfile.add(cancel);

		submit.addActionListener(e -> {
			String password = passwordField.getText();
			String newPassword = newPasswordField.getText();
			if (!new UserController().changePassword(user.getId(), password, newPassword, "passenger")) {
				JOptionPane.showMessageDialog(null, "Gagal mengganti password, silahkan input password sebelumnya dengan benar.");
			} else {
				JOptionPane.showMessageDialog(null, "Berhasil update password!");
				this.dispose();
				new view.passenger.profile.ProfileUser(user.getId());
			}
		});

		cancel.addActionListener(e -> {
			this.dispose();
			new view.passenger.profile.ProfileUser(user.getId());
		});

		add(formProfile);
	}
}
