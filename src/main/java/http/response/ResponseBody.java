package http.response;

public class ResponseBody {
    private byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }
}
