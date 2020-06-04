package http;

public class HttpResponse {
    private final int statusCode;
    private final ContentType contentType;
    private final byte[] responseBody;

    private HttpResponse(final int statusCode, final ContentType contentType, final byte[] responseBody) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.responseBody = responseBody;
    }

    public static HttpResponse ok(final ContentType contentType, final byte[] responseBody) {
        return new HttpResponse(200, contentType, responseBody);
    }

    public ContentType getContentType() {
        return contentType;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getBodyLength() {
        return responseBody.length;
    }

    public byte[] getBody() {
        return responseBody;
    }
}
