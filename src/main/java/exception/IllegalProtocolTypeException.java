package exception;

public class IllegalProtocolTypeException extends IllegalArgumentException {

    private static final String MESSAGE = "invalid protocol type: (%s)";

    public IllegalProtocolTypeException(String type) {
        super(String.format(MESSAGE, type));
    }
}
