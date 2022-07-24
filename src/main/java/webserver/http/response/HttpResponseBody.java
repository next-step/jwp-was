package webserver.http.response;

public class HttpResponseBody {
    private byte[] bodyBytes;

    public HttpResponseBody(byte[] bodyBytes) {
        this.bodyBytes = bodyBytes;
    }
}
