package http.request;

import http.request.body.Body;
import http.request.headers.Headers2;
import http.request.requestline.requestLine2.RequestLine2;

public class Request {
    private RequestLine2 requestLine;
    private Headers2 headers;
    private Body body;

    public Request(RequestLine2 requestLine, Headers2 headers, Body body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public String getUrl() {
        return requestLine.getUrl();
    }

    public RequestLine2 getRequestLine() {
        return requestLine;
    }

    public Headers2 getHeaders() {
        return headers;
    }

    public Body getBody() {
        return body;
    }

    public String getParameter(String key){
        return getHeaders().getParameter(key);
    }
}
