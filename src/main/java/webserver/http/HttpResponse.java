package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dos;
    private Map<String, String> headers = new HashMap<>();

    private HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public static HttpResponse from(OutputStream out) {
        return new HttpResponse(out);
    }

    public void addHeader(String header, String value) {
        headers.put(header, value);
    }

    public void responseBody(byte[] body) throws IOException {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            addHeader("Content-Type", "text/html;charset=utf-8");
            addHeader("Content-Length", String.valueOf(body.length));
            responseHeader();
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            addHeader("Location", location);
            responseHeader();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader() {
        headers.forEach((key, value) -> {
            try {
                dos.writeBytes(key + ": " + value + "\r\n");
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        });
    }
}
