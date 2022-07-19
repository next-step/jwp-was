package exception;

public class UnSupportedHttpMethodException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "지원하지 않는 Http Method 입니다 : %s";

    public UnSupportedHttpMethodException(final String message) {
        super(String.format(DEFAULT_MESSAGE, message));
    }

}
