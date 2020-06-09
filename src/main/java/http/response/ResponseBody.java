package http.response;

public class ResponseBody {

    private byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public static ResponseBody emptyBody() {
        return new ResponseBody(new byte[0]);
    }


    public byte[] getBody() {
        return body;
    }

    public int getLength() {
        return body.length;
    }

}
