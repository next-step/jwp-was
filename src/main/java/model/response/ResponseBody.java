package model.response;

public class ResponseBody {
    private byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public int getLength() {
        return body.length;
    }
}
