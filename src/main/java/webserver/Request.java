package webserver;

import webserver.http.Header;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Request {
    private RequestLine requestLine;
    private Header header;

    public Request(List<String> requests) {
        this.requestLine = new RequestLine(requests.get(0));
        requests.remove(0);
        this.header = new Header(requests);
    }

    public Request(RequestLine requestLine, Header header) {
        this.requestLine = requestLine;
        this.header = header;
    }

    public String getRequestPath() {
        return requestLine.getPathWithoutQueryString();
    }

    public Map<String, String> getHeader() {
        return header.getHeader();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(requestLine, request.requestLine) && Objects.equals(header, request.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, header);
    }
}
