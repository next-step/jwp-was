package webserver.http;

import java.util.Objects;

public class StatusLine {

    private final ProtocolVersion protocolVersion;

    private final Status status;

    public StatusLine(ProtocolVersion protocolVersion, Status status) {
        this.protocolVersion = protocolVersion;
        this.status = status;
    }

    public StatusLine(Status status) {
        this(ProtocolVersion.HTTP11, status);
    }

    String getMessage() {
        return protocolVersion.getMessage() + " " + status.getMessage();
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusLine that = (StatusLine) o;
        return Objects.equals(protocolVersion, that.protocolVersion) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocolVersion, status);
    }

    @Override
    public String toString() {
        return protocolVersion.toString() + " " + status.toString();
    }
}
