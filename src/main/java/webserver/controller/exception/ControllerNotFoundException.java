package webserver.controller.exception;

public class ControllerNotFoundException extends RuntimeException {
    public ControllerNotFoundException(String url) {
        super("컨트롤러를 찾을 수 없습니다. url = " + url);
    }
}
