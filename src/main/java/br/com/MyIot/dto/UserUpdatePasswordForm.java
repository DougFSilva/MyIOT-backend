package br.com.MyIot.dto;

public class UserUpdatePasswordForm {

	private String userId;

	private String currentPassword;

	private String newPassword;

	public UserUpdatePasswordForm(String userId, String currentPassword, String newPassword) {
		this.userId = userId;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
	}

	public String getUserId() {
		return userId;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	@Override
	public String toString() {
		return "UpdateUserPasswordForm [userId=" + userId + ", currentPassoword=" + currentPassword + ", newPassword="
				+ newPassword + "]";
	}

}
