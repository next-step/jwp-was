package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private Map<String, Object> headers;
    private DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        headers = new HashMap<>();
        this.dos = dos;
    }

    public void response200Header() throws IOException {
        dos.writeBytes("HTTP/1.1 200 \r\n");
        writeHeaders();
        dos.writeBytes("\r\n");
    }

    public void response302Header() throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found \r\n");
        writeHeaders();
        dos.writeBytes("\r\n");
    }

    public void addHeader(String key, Object value) {
        headers.put(key, value);
    }

    private void writeHeaders() throws IOException {
        for (String key : headers.keySet()) {
            dos.writeBytes(key + ": " + headers.get(key) + " \r\n");
        }
    }

    public void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}
