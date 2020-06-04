package http;

public class HttpResponse {
    private final int statusCode;
    private final String contentType;
    private final byte[] responseBody;

    public HttpResponse(final int statusCode, final String contentType, final byte[] responseBody) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.responseBody = responseBody;
    }

    public static HttpResponse ok(final String contentType, final byte[] responseBody) {
        return new HttpResponse(200, contentType, responseBody);
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
