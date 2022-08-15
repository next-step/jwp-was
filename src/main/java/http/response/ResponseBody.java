package http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseBody {

    private byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public int getContentLength() {
        return body.length;
    }
}
