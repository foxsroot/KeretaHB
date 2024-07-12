package view.passenger.profile;

import controller.AuthenticationHelper;
import controller.UserController;
import model.classes.Passenger;
import view.Login;

import javax.swing.*;
import java.awt.*;

public class ProfileUser extends JFrame {
	public ProfileUser(int userId) {
		Passenger profileUser = new UserController().getUser(userId);

		this.setVisible(true);
		this.setSize(900, 700);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Your Profile");

		JLabel screenTitle = new JLabel("Profile");
		screenTitle.setFont(new Font("calibri", Font.BOLD, 44));
		screenTitle.setBounds(143, 22, 200, 51);

		JPanel formLogin = new JPanel();
		formLogin.setLayout(null);
		formLogin.setBounds(44, 111, 300, 700);

		JLabel usernameUser = new JLabel("Username: " + profileUser.getName());
		usernameUser.setFont(new Font("calibri", Font.PLAIN, 17));
		usernameUser.setBounds(88, 0, 400, 30);
		formLogin.add(usernameUser);

		JLabel emailUser = new JLabel("Email: " + profileUser.getEmail());
		emailUser.setFont(new Font("calibri", Font.PLAIN, 17));
		emailUser.setBounds(88, 50, 400, 30);
		formLogin.add(emailUser);

		JLabel phoneUser = new JLabel("Phone: " + profileUser.getCellphone());
		phoneUser.setFont(new Font("calibri", Font.PLAIN, 17));
		phoneUser.setBounds(88, 100, 400, 30);
		formLogin.add(phoneUser);

		JLabel loyaltyUser = new JLabel("Loyalty: " + profileUser.getLoyalty());
		loyaltyUser.setFont(new Font("calibri", Font.PLAIN, 17));
		loyaltyUser.setBounds(88, 150, 400, 30);
		formLogin.add(loyaltyUser);

		JLabel totalPaidUser = new JLabel("Total Paid: " + profileUser.getTotalPaid());
		totalPaidUser.setFont(new Font("calibri", Font.PLAIN, 17));
		totalPaidUser.setBounds(88, 200, 400, 30);
		formLogin.add(totalPaidUser);

		JPanel buttonProfile = new JPanel();
		buttonProfile.setLayout(null);
		buttonProfile.setBounds(44, 420, 500, 200);

		JButton editProfile = new JButton("Edit Profile");
		editProfile.setBounds(0, 0, 100, 50);
		buttonProfile.add(editProfile);

		editProfile.addActionListener(e -> {
			this.dispose();
			new EditProfileUser(profileUser);
		});

		JButton changePassword = new JButton("Change Password");
		changePassword.setBounds(150, 0, 200, 50);
		buttonProfile.add(changePassword);

		JButton logout = new JButton("Log Out");
		logout.setBounds(400, 0, 100, 50);
		buttonProfile.add(logout);

		add(screenTitle);
		add(formLogin);
		add(buttonProfile);
	}

	public static void main(String[] args) {
		int userId = AuthenticationHelper.getInstance().getUserId();
		if (userId != 0){
			new ProfileUser(userId);
		} else {
			new Login();
		}
	}
}
