package it.sitissimo.validation.service.errors;

public class ValidationException extends RuntimeException {
    private String message;

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
        this.message = message;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message;
    }
}
