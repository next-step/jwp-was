package http.request;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class RequestLine {
    private String requestLine;

    public RequestLine(String requestLine) {
        this.requestLine = requestLine;
    }

    public Method getMethod() {
        return Method.match(this.requestLine);
    }

    public String getUrl() {
        return Path.getUrl(this.requestLine);
    }

    public Map<String, String> getQueries() throws UnsupportedEncodingException {
        if (Method.hasQueryString(this.requestLine)) {
            return Path.getQueries(requestLine);
        }
        return Collections.EMPTY_MAP;
    }

    public String getProtocol() {
        return Protocol.getProtocol(this.requestLine);
    }

    public String getVersion() {
        return Protocol.getVersion(this.requestLine);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(requestLine, that.requestLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine);
    }
}
