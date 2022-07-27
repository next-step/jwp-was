package webserver.response;

import java.util.Objects;
import webserver.request.Protocol;

public class StatusLine {

    private final Protocol protocol;
    private final StatusCode statusCode;

    public StatusLine(Protocol protocol, StatusCode statusCode) {
        this.protocol = protocol;
        this.statusCode = statusCode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StatusLine that = (StatusLine) o;
        return Objects.equals(protocol, that.protocol) && statusCode == that.statusCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, statusCode);
    }
}
