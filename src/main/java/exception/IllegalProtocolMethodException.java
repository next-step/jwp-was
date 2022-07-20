package exception;

public class IllegalProtocolMethodException extends IllegalArgumentException {

    private static final String MESSAGE = "invalid protocol method: (%s)";

    public IllegalProtocolMethodException(String method) {
        super(String.format(MESSAGE, method));
    }
}
