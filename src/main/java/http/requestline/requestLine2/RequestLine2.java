package http.requestline.requestLine2;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class RequestLine2 {
    private String requestLine;

    public RequestLine2(String requestLine) {
        this.requestLine = requestLine;
    }

    public Method getMethod() {
        return Method.match(this.requestLine);
    }

    public String getUrl() {
        return Path2.getUrl(this.requestLine);
    }

    public Map<String, String> getQueries() {
        if (Method.isGet(this.requestLine)) {
            return Path2.getQueries(requestLine);
        }
        return Collections.EMPTY_MAP;
    }

    public String getProtocol() {
        return Protocol2.getProtocol(this.requestLine);
    }

    public String getVersion() {
        return Protocol2.getVersion(this.requestLine);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine2 that = (RequestLine2) o;
        return Objects.equals(requestLine, that.requestLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine);
    }
}
