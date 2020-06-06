package http;

import java.util.Objects;

public class HttpRequest {

    private final RequestLine requestLine;

    HttpRequest(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public static HttpRequest of(String line) {
        return new HttpRequest(RequestLine.of(line));
    }

    String getPath() {
        return requestLine.getPath();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(requestLine, that.requestLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine);
    }
}
