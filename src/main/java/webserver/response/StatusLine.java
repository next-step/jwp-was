package webserver.response;

import webserver.enums.Protocol;
import webserver.enums.HttpStatus;

public class StatusLine {

    private final Protocol protocol;
    private final HttpStatus httpStatus;

    public StatusLine(Protocol protocol, HttpStatus httpStatus) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public HttpStatus getStatusCode() {
        return this.httpStatus;
    }

    @Override
    public String toString() {
        return protocol.protocol() + "/" + protocol.version() + " " + httpStatus.getStatusCode() + " " + httpStatus.getMessage();
    }

}
