package webserver.http.response;

import webserver.http.HttpStatus;
import webserver.http.HttpVersion;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResponseLine {

    private static final HttpVersion httpVersion = HttpVersion.HTTP1_1;
    private static final String FORMAT = "%s %s";

    private static final Map<HttpStatus, ResponseLine> CACHE = new HashMap<>();

    private final HttpStatus httpStatus;

    private ResponseLine(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public static ResponseLine of(final HttpStatus httpStatus) {
        return CACHE.computeIfAbsent(httpStatus, ResponseLine::new);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponseLine)) {
            return false;
        }

        final ResponseLine that = (ResponseLine) o;
        return httpStatus == that.httpStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpStatus);
    }

    @Override
    public String toString() {
        return String.format(FORMAT, httpVersion, httpStatus);
    }
}
