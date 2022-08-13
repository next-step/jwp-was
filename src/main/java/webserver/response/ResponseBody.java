package webserver.response;

public class ResponseBody {

    private byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public static ResponseBody parse(String value) {
        return new ResponseBody(value.getBytes());
    }

    public byte[] getBody() {
        return body;
    }
}
