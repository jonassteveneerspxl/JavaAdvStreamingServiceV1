package be.pxl.ja.streamingservice.exception;

public class AccountNotFoundException extends Exception {

	public AccountNotFoundException(String email) {
		super("Account with email [" + email + "] already exists");
	}
}
