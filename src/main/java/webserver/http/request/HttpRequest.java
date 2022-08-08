package webserver.http.request;

import webserver.http.request.RequestBody;
import webserver.http.request.RequestHeader;
import webserver.http.request.RequestLine;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
