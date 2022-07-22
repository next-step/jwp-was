package exception;

public class InvalidProtocolException extends RuntimeException {

    private static final String MESSAGE = "invalid protocol suite: (%s)";

    public InvalidProtocolException(String suite) {
        super(String.format(MESSAGE, suite));
    }
}
