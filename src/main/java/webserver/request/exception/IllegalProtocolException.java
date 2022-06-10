package webserver.request.exception;

public class IllegalProtocolException extends RuntimeException {
    private static final String MESSAGE = "%s; 부적절한 Protocol 입니다.";

    public IllegalProtocolException(String protocol) {
        super(String.format(MESSAGE, protocol));
    }
}
