package webserver.common.exception;

public class IllegalQueryStringException extends RuntimeException {
    private static final String MESSAGE = "%s; 부적절한 QueryString 입니다.";

    public IllegalQueryStringException(String queryString) {
        super(String.format(MESSAGE, queryString));
    }
}
