package exception;

public class IllegalProtocolVersionException extends IllegalArgumentException {

    private static final String MESSAGE = "invalid protocol version: (%s)";

    public IllegalProtocolVersionException(String version) {
        super(String.format(MESSAGE, version));
    }
}
