package webserver.controller.exception;

public class UnsupportedHttpMethodException extends RuntimeException {

    public UnsupportedHttpMethodException() {
        super("지원하지않는 http method 입니다");
    }
}
