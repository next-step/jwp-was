package http.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static http.response.ResponseStatus.FOUND;
import static http.response.ResponseStatus.OK;

public class Response {

    private static final String STATUS_PREFIX = "HTTP/1.1";

    private ResponseStatus status;
    private ResponseHeader header;
    private byte[] body;

    public Response(ResponseStatus status, ResponseHeader header, byte[] body) {
        this.status = status;
        this.header = header;
        this.body = body;
    }

    public static Response ofOk(byte[] bytes) {
        ResponseHeader header = new ResponseHeader();
        header.putContentType();
        header.putContentLength(bytes.length);
        return new Response(OK, header, bytes);
    }

    public static Response ofFound(String redirectUrl) {
        ResponseHeader header = new ResponseHeader();
        header.putLocation(redirectUrl);
        return new Response(FOUND, header, new byte[0]);
    }

    public void putHeader(String key, String value) {
        this.header.putHeader(key, value);
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public Map<String, String> getHeader() {
        return header.getHeader();
    }

    public byte[] getBody() {
        return body;
    }

    public int getBodyLength() {
        return body.length;
    }

    public String makeStatus() {
        return String.format("%s %s %s \r\n", STATUS_PREFIX, status.getCode(), status.getText());
    }

    public List<String> makeHeader() {
        List<String> response = new ArrayList<>();
        for (String key : this.getHeader().keySet()) {
            response.add(String.format("%s: %s\r\n", key, getHeader().get(key)));
        }
        return response;
    }

    public void putCookie(String cookie) {
        this.header.putCookie(cookie);
    }
}
