package http.httpresponse;

import java.util.Objects;

public class StatusLine {
    private final HttpStatusCode httpStatusCode;

    public StatusLine(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public int getStatusCode() {
        return httpStatusCode.getCode();
    }

    public String getStatusMessage() {
        return httpStatusCode.getMessage();
    }

    @Override
    public String toString() {
        return String.join(" ", String.valueOf(getStatusCode()), getStatusMessage());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusLine that = (StatusLine) o;
        return httpStatusCode == that.httpStatusCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpStatusCode);
    }
}
