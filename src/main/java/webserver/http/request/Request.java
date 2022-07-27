package webserver.http.request;

public class Request {
    private final RequestLine requestLine;
    private final Headers headers;

    public Request(RequestLine requestLine, Headers headers) {
        this.requestLine = requestLine;
        this.headers = headers;
    }

    public boolean hasContents() {
        return headers.containsContentLength();
    }

    public int getContentLength() {
        return headers.getContentLength();
    }

    public void addParameters(Parameters parameters) {
        requestLine.addParameters(parameters);
    }

    public boolean hasMethod(Method method) {
        return requestLine.hasMethod(method);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getParameter(String key) {
        return requestLine.getParameter(key);
    }

    public boolean hasContentType(String contentType) {
        return headers.hasContentType(contentType);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                '}';
    }
}
