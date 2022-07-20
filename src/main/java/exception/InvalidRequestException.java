package exception;

public class InvalidRequestException extends RuntimeException {
    private static final String errorMessage = "Wrong request with %s";

    public InvalidRequestException(String value) {
        super(String.format(errorMessage, value));
    }
}
