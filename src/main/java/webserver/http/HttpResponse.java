package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;
    private ResponseHeader headers;
    private final Map<String, Object> attributes = new HashMap<>();

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.headers = new ResponseHeader();
    }

    public void addHeader(String key, String value) {
        headers.addHeader(key, value);
    }

    public void updateStatus(HttpStatus status) {
        headers.updateStatus(status);
    }

    public HttpStatus getStatus() {
        return headers.getStatus();
    }

    public void response(byte[] contents) {
        responseHeader();
        responseBody(contents);
    }

    private void responseHeader() {
        try {
            processStatusLine();
            processHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String location) {
        updateStatus(HttpStatus.FOUND);
        addHeader("Location", location);
        responseHeader();
    }

    private void processStatusLine() throws IOException {
        dos.writeBytes("HTTP/1.1 " + headers.getStatusMessage() + " \r\n");
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processHeaders() throws IOException {
        Set<String> keys = headers.getHeaderKeys();
        for (String key : keys) {
            dos.writeBytes(key + ": " + headers.getHeader(key) + "\r\n");
        }
    }

    public void addAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
