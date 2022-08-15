package webserver.http.response;

import webserver.http.HttpStatus;
import webserver.http.Protocol;

public class ResponseLine {

    private final Protocol protocol;
    private final HttpStatus httpStatus;

    public ResponseLine(Protocol protocol, HttpStatus httpStatus) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
    }

    public ResponseLine(String protocol, HttpStatus httpStatus) {
        this.protocol = new Protocol(protocol);
        this.httpStatus = httpStatus;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getProtocolAndVersion() {
        return protocol.getProtocolAndVersion();
    }
}
