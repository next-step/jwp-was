package webserver.exception;

public class NoSuchResolverException extends RuntimeException {
    public static final String DEFAULT_EXCEPTION_MSG = "유효한 리졸버를 찾을 수 없었습니다. :";

    public NoSuchResolverException(String viewName) {
        super(DEFAULT_EXCEPTION_MSG + viewName);
    }
}
