package controller;

public class AuthenticationHelper {
	private static AuthenticationHelper instance;
	private int userId;
	private String userRole;

	public static AuthenticationHelper getInstance() {
		if (instance == null) {
			synchronized (AuthenticationHelper.class) {
				if (instance == null) {
					instance = new AuthenticationHelper();
				}
			}
		}
		return instance;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
}
