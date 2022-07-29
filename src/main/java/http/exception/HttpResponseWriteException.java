package http.exception;

public class HttpResponseWriteException extends RuntimeException {
    public HttpResponseWriteException(String message) {
        super("HttpResponse 쓰기 예외가 발생했습니다. message=" + message);
    }
}
