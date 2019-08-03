package webserver.http;

public class HttpResponse {
    private byte[] body;

    public HttpResponse(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return this.body;
    }
}
