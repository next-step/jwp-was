package webserver.http.request;

import java.util.HashMap;

public class Request {
    private final RequestLine requestLine;
    private final Headers headers;
    private final Parameters requestBody;

    public Request(RequestLine requestLine, Headers headers) {
        this(requestLine, headers, new Parameters(new HashMap<>()));
    }

    public Request(RequestLine requestLine, Headers headers, Parameters requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    public boolean hasContents() {
        return headers.containsContentLength();
    }

    public int getContentLength() {
        return headers.getContentLength();
    }

    public void addBody(Parameters requestBody) {
        this.requestBody.add(requestBody);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                ", requestBody=" + requestBody +
                '}';
    }
}
