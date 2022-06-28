package webserver.exception;

public class UnSupportedHttpMethodException extends RuntimeException {
    public UnSupportedHttpMethodException() {
        super("지원하지 않은 메소드 형식 입니다.");
    }
}
