package webserver.http.response.body;

public class HttpResponseBody {

    private byte[] body;

    public HttpResponseBody(byte[] body) {
        this.body = body;
    }

    public static HttpResponseBody empty() {
        return new HttpResponseBody(new byte[0]);
    }

    public byte[] getBody() {
        return body;
    }

    public void addBody(byte[] bytes) {
        this.body = bytes;
    }
}
