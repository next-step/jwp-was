package http.exception;

public class NotFoundExtensionException extends RuntimeException {
    public NotFoundExtensionException(String url) {
        super("확장자를 찾을 수 없습니다. url =" + url);
    }
}
