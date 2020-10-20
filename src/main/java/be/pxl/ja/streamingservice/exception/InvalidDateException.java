package be.pxl.ja.streamingservice.exception;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InvalidDateException extends RuntimeException {

	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public InvalidDateException(LocalDate incorrectDate, String type, String description) {
		super(FORMATTER.format(incorrectDate) + " is not a valid " + type + ". " + description);
	}
}
