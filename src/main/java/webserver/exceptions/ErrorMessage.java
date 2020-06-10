package webserver.exceptions;

public enum ErrorMessage {
    ILLEGAL_COOKIE_HEADER("유효하지 않은 쿠키 헤더값 입니다."),
    ILLEGAL_PARAMETER("유효하지 않은 FormData / QueryString 입니다."),
    UNSSUPORTED_ENCODING("지원하지 않는 Encoding 타입 입니다.."),
    REQUEST_PROCESSABLE_CONTROLLER_NOT_FOUND("해당 요청을 처리할 컨트롤러를 찾지 못했습니다."),
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
