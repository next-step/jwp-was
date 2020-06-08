package http.response;

import http.HttpStatus;
import http.request.requestline.Protocol;

public class StatusLine {
    private final Protocol protocol;
    private final HttpStatus httpStatus;

    public static StatusLine of(Protocol protocol, HttpStatus httpStatus) {
        return new StatusLine(protocol, httpStatus);
    }

    public StatusLine(Protocol protocol, HttpStatus httpStatus) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return protocol.toString() + httpStatus.toString();
    }
}
