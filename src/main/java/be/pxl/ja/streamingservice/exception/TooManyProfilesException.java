package be.pxl.ja.streamingservice.exception;

public class TooManyProfilesException extends RuntimeException {

    public TooManyProfilesException() {
    }

    public TooManyProfilesException(String message) {
        super(message);
    }

    public TooManyProfilesException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyProfilesException(Throwable cause) {
        super(cause);
    }

    public TooManyProfilesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
