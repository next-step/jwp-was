package http.response;

import http.common.HttpStatus;
import http.request.requestline.Protocol;

public class StatusLine {
    private final Protocol protocol;
    private final HttpStatus httpStatus;

    public StatusLine(Protocol protocol, HttpStatus httpStatus) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
    }

    public static StatusLine of(Protocol protocol, HttpStatus httpStatus) {
        return new StatusLine(protocol, httpStatus);
    }

    @Override
    public String toString() {
        return protocol.toString() + " " + httpStatus.toString();
    }
}
