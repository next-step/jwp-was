package webserver.http.response.statusline;

import webserver.http.request.requestline.Protocol;

public class StatusLine {
    private Protocol protocol;
    private StatusCode statusCode;

    public StatusLine(Protocol protocol, StatusCode statusCode) {
        validate(protocol, statusCode);
        this.protocol = protocol;
        this.statusCode = statusCode;
    }

    public static StatusLine of(Protocol protocol, StatusCode statusCode) {
        return new StatusLine(protocol, statusCode);
    }

    public boolean isStatusCodeEqual(StatusCode statusCode) {
        return this.statusCode.equals(statusCode);
    }

    private static void validate(Protocol protocol, StatusCode statusCode) {
        validateProtocol(protocol);
        validateStatusCode(statusCode);
    }

    private static void validateStatusCode(StatusCode statusCode) {
        if (statusCode == null) {
            throw new IllegalArgumentException("응답 코드는 null 일 수 없습니다.");
        }
    }

    private static void validateProtocol(Protocol protocol) {
        if (protocol == null) {
            throw new IllegalArgumentException("응답 프로토콜은 null 일 수 없습니다.");
        }
    }

    public String statusLine() {
        return String.format("%s %s %s\r\n", this.protocol.protocol(), this.statusCode.statusCode(), this.statusCode.statusMessage());
    }
}
