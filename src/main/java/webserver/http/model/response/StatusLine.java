package webserver.http.model.response;

import webserver.http.model.request.Protocol;

public class StatusLine {
    private final Protocol protocol;
    private final StatusCode statusCode;

    public StatusLine(StatusCode statusCode) {
        this(Protocol.httpProtocol(), statusCode);
    }

    public StatusLine(Protocol protocol, StatusCode statusCode) {
        this.protocol = protocol;
        this.statusCode = statusCode;
    }

    public String statusLineText() {
        return protocol.getProtocol() + " " + statusCode.statusCodeText() + " \n";
    }
}
