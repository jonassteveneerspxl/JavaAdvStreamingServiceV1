package be.pxl.ja.streamingservice.model;

public enum CreditCardType {
	VISA(4),
	MASTERCARD(5);

	private int firstNumber;

	CreditCardType(int firstNumber) {
		this.firstNumber = firstNumber;
	}

	public int getFirstNumber() {
		return firstNumber;
	}
}

