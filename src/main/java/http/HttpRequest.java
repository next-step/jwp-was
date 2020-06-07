package http;

public class HttpRequest {

    private final RequestLine requestLine;

    public HttpRequest(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public String getPath(){
        return requestLine.getPath();
    }

}
