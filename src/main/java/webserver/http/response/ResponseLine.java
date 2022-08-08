package webserver.http.response;

import webserver.http.ProtocolVersion;

public class ResponseLine {

    private final ProtocolVersion protocolVersion = ProtocolVersion.ofServerProtocolVersion();
    private final StatusCode statusCode;

    public ResponseLine(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public static ResponseLine of200() { return new ResponseLine(StatusCode.OK); }

    public static ResponseLine of302() {
        return new ResponseLine(StatusCode.FOUND);
    }

    public static ResponseLine of404() { return new ResponseLine(StatusCode.NOT_FOUND); }

    public ProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public String toPrint(){
        return protocolVersion.getProtocol().toString() + "/" + protocolVersion.getVersion().getName() + " "
                + statusCode.getStatus() + " " + statusCode.name() + " \r\n";
    }
}
