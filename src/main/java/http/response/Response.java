package http.response;

public class Response {
    private HttpStatus status;
    private ContentType contentType;
    private ResponseBody body;

    public Response(HttpStatus status, ContentType contentType, ResponseBody body) {
        this.status = status;
        this.contentType = contentType;
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

    public String getHeaderByKey(String s) {
        return "";
    }
}
