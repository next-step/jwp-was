package webserver.http;

import java.io.DataOutputStream;
import java.util.Map;

public class HttpResponse {

    private static final String NEW_LINE = System.lineSeparator();

    private HttpStatusCode httpStatusCode;
    private Map<String, String> headers;
    private byte[] body;

    public HttpResponse(HttpStatusCode httpStatusCode, Map<String, String> headers, byte[] body) {
        this.httpStatusCode = httpStatusCode;
        this.headers = headers;
        this.body = body;
    }

    public static HttpResponse ok(Map<String, String> headers, byte[] body) {
        return new HttpResponse(HttpStatusCode.OK, headers, body);
    }

    public static HttpResponse redirect(Map<String, String> headers, byte[] body) {
        return new HttpResponse(HttpStatusCode.FOUND, headers, body);
    }

    public void write (DataOutputStream dos) throws Exception {
        dos.writeBytes("HTTP/1.1 " + httpStatusCode.getStatusCode() + " " + httpStatusCode.getDescription() + NEW_LINE);
        dos.writeBytes("Content-Type: " + headers.get("Content-Type") + NEW_LINE);
        dos.writeBytes("Content-Length: " + body.length + NEW_LINE);
        if (headers.containsKey("Location")) {
            dos.writeBytes("Location: " + headers.get("Location") + NEW_LINE);
        }
        if (headers.containsKey("Set-Cookie")) {
            dos.writeBytes("Set-Cookie: " + headers.get("Set-Cookie") + NEW_LINE);
        }
        dos.writeBytes(NEW_LINE);
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public HttpStatusCode getStatusCode() {
        return httpStatusCode;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public byte[] getBody() {
        return body;
    }
}