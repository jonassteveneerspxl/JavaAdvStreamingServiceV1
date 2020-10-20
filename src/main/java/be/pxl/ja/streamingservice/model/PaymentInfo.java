package be.pxl.ja.streamingservice.model;

import java.time.LocalDate;

public class PaymentInfo {
	private String cardNumer;
	private CreditCardType type;
	private String firstName;
	private String lastName;
	private LocalDate expirationDate;
	private int securityCode;

	public String getCardNumer() {
		return cardNumer;
	}

	public void setCardNumer(String cardNumer) {

		this.cardNumer = cardNumer;
	}

	public CreditCardType getType() {
		return type;
	}

	public void setType(CreditCardType type) {
		this.type = type;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(int securityCode) {
		this.securityCode = securityCode;
	}
}
