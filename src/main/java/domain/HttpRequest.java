package domain;

import java.util.List;
import java.util.Objects;

public class HttpRequest {
    private final List<String> lines;
    private final RequestLine requestLine;

    public HttpRequest(List<String> lines) {
        this.lines = lines;
        this.requestLine = new RequestLine(lines.get(0));
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(lines, that.lines) && Objects.equals(requestLine, that.requestLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lines, requestLine);
    }
}
