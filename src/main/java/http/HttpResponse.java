package http;

public class HttpResponse {
    private final int statusCode;
    private final byte[] responseBody;

    public HttpResponse(final int statusCode, final byte[] responseBody) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getBodyLength() {
        return responseBody.length;
    }

    public byte[] getBody() {
        return responseBody;
    }
}
