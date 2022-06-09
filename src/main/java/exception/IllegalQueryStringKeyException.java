package exception;

public class IllegalQueryStringKeyException extends RuntimeException {
    private static final String MESSAGE = "%s; 부적절한 QueryString 의 키입니다.";

    public IllegalQueryStringKeyException(String key) {
        super(String.format(MESSAGE, key));
    }
}
