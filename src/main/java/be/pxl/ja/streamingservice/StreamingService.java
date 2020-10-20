package be.pxl.ja.streamingservice;

import be.pxl.ja.streamingservice.exception.AccountNotFoundException;
import be.pxl.ja.streamingservice.exception.DuplicateEmailException;
import be.pxl.ja.streamingservice.exception.InvalidPasswordException;
import be.pxl.ja.streamingservice.model.Account;
import be.pxl.ja.streamingservice.model.Content;
import be.pxl.ja.streamingservice.model.StreamingPlan;
import be.pxl.ja.streamingservice.repository.AccountRepository;
import be.pxl.ja.streamingservice.repository.ContentRepository;

import java.time.LocalDate;
import java.util.List;

public class StreamingService {

	private ContentRepository contentRepository = new ContentRepository();
	private AccountRepository accountRepository = new AccountRepository();

	public StreamingService() throws DuplicateEmailException {
		Account testAccount = new Account("test@pxl.be", "test123");
		testAccount.setStreamingPlan(StreamingPlan.PREMIUM);
		testAccount.getProfiles().get(0).setDateOfBirth(LocalDate.of(2000, 5, 8));
		addAccount(testAccount);
	}

	public void addAccount(Account account) throws DuplicateEmailException {
		accountRepository.addAccount(account);
	}

	public List<Content> getContentList() {
		return contentRepository.getContentList();
	}

	public Account verifyAndGetAccount(String email, String password) throws AccountNotFoundException, InvalidPasswordException {
		Account account = accountRepository.findAccount(email);
		if (account == null) {
			throw new AccountNotFoundException(email);
		}
		if (!account.verifyPassword(password)) {
			throw new InvalidPasswordException();
		}
		return account;
	}
}
