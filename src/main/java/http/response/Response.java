package http.response;

import http.request.headers.Headers2;

public class Response {
    private HttpStatus status;
    private ContentType contentType;
    private ResponseBody body;
    private Headers2 headers;

    public Response(HttpStatus status, ContentType contentType, ResponseBody body) {
        this.status = status;
        this.contentType = contentType;
        this.body = body;
    }

    public Response(HttpStatus status, ContentType contentType, Headers2 headers, ResponseBody body) {
        this.status = status;
        this.contentType = contentType;
        this.headers = headers;
        this.body = body;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body.getBody();
    }

    public String getHeaderByKey(String key) {
        return headers.getParameter(key);
    }

    public Headers2 getHeaders() {
        return headers;
    }
}
