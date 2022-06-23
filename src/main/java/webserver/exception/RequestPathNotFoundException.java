package webserver.exception;

public class RequestPathNotFoundException extends RuntimeException {
    public RequestPathNotFoundException(String message) {
        super(String.format("지원하지 않는 요청입니다. : %s", message));
    }
}
