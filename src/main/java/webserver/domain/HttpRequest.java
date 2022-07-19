package webserver.domain;

public class HttpRequest {
    public static final int REQUEST_LINE_POINT = 0;
    public static final int HEADER_START_POINT = 1;
    private RequestLine requestLine;
    private HttpHeaders headers;
    private RequestBody requestBody;

    public HttpRequest(){}

    public HttpRequest(RequestLine requestLine, HttpHeaders headers, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    public static HttpRequest newInstance(String line) {
        String[] attributes = line.split(System.lineSeparator());

        return new HttpRequest(RequestLine.from(attributes[REQUEST_LINE_POINT]),
                HttpHeaders.newInstance(attributes, HEADER_START_POINT, attributes.length - 1),
                new RequestBody(attributes[attributes.length - 1]));
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
