package http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseBody {

    private byte[] body;
    private Map<String, Object> attributes = new HashMap<>();

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public int getContentLength() {
        return body.length;
    }

    public void addAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Map<String, Object> getBodyAttributes() {
        return attributes;
    }
}
