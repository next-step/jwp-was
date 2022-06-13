package webserver;

public class Request {
    private RequestLine requestLine;

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Request(final String requestLine) {
        this.requestLine = new RequestLine(requestLine);
    }
}
