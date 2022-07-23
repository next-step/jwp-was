package webserver.http;

public class StatusLine {

    private final ProtocolVersion protocolVersion;

    private final Status status;

    StatusLine(ProtocolVersion protocolVersion, Status status) {
        this.protocolVersion = protocolVersion;
        this.status = status;
    }

    String getMessage() {
        return protocolVersion.getMessage() + " " + status.getMessage();
    }
}
