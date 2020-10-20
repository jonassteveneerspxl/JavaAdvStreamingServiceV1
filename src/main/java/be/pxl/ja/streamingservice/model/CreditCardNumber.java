package be.pxl.ja.streamingservice.model;

public class CreditCardNumber {
	private static final int LENGTH = 16;
	private static final int CVC_LENGTH = 3;

	private CreditCardType type;
	private String number;
	private String cvc;

	public CreditCardNumber(String number, String cvc) {
		number = removeBlanks(number);
		if (!isNumeric(number) || number.length() != LENGTH) {
			throw new IllegalArgumentException("A card number must have " + LENGTH + " digits.");
		}
		this.number = number;
		type = getCreditCardType(number);
		if (type == null) {
			throw new IllegalArgumentException("This is not a valid credit card.");
		}
		this.cvc = removeBlanks(cvc);
	}

	public CreditCardType getType() {
		return type;
	}

	public String getCvc() {
		return cvc;
	}

	public String getNumber() {
		return number;
	}

	private String removeBlanks(String text) {
		return text.replaceAll("\\s","");
	}

	private boolean isNumeric(String text) {
		if (text == null || text.length() == 0) {
			return false;
		}
		try {
			Long.parseLong(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private CreditCardType getCreditCardType(String number) {
		for (CreditCardType cardType : CreditCardType.values()) {
			if (cardType.getFirstNumber() == Integer.parseInt(number.substring(0, 1))) {
				return cardType;
			}
		}
		return null;
	}


}
