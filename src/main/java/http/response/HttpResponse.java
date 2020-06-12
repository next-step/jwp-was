package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private static final String SEPARATOR = ": ";
    private static final String NEW_LINE = "\r\n";

    private final Map<String, String> responseHeaders;
    private final DataOutputStream dos;

    public HttpResponse(final OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.responseHeaders = new HashMap<>();
    }

    public void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK " + NEW_LINE);
            addHeader("Content-Length", Integer.toString(lengthOfBodyContent));
            setHeader();
            dos.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response400Header() {
        try {
            dos.writeBytes("HTTP/1.1 404 NotFound " + NEW_LINE);
            setHeader();
            dos.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void setHeader() {
        try {
            for (String key : responseHeaders.keySet()) {
                String value = responseHeaders.get(key);
                String header = key + SEPARATOR + value + NEW_LINE;

                dos.writeBytes(header);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public void forward(String path) {
        byte[] body = path.getBytes();
        response200Header(body.length);
        responseBody(body);
    }

    public void sendRedirect(String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found " + NEW_LINE);
            setHeader();
            dos.writeBytes("Location: " + location + NEW_LINE);
            dos.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void addHeader(String key, String value) {
        this.responseHeaders.put(key, value);
    }
}
