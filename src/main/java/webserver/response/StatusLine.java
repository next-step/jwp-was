package webserver.response;

import webserver.enums.Protocol;
import webserver.enums.StatusCode;

public class StatusLine {

    private final Protocol protocol;
    private final StatusCode statusCode;

    public StatusLine(Protocol protocol, StatusCode statusCode) {
        this.protocol = protocol;
        this.statusCode = statusCode;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public StatusCode getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String toString() {
        return protocol.protocol() + "/" + protocol.version() + " " + statusCode.getStatusCode() + " " + statusCode.getMessage();
    }

}
