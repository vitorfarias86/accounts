package com.pismo.application.exception;

public class AccountsError {

	private int status;
	private String message;

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public AccountsError(int status, String message) {
		this.status = status;
		this.message = message;
	}

	@Override
	public String toString() {
		return "{" +
				"status=" + status +
				", message='" + message + '\'' +
				'}';
	}
}
