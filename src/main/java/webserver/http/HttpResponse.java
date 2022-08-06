package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public void addHeader(String headerName, String headerValue) {
        this.headers.put(headerName, headerValue);
    }

    public void forward(String url) {
        try {
            byte[] body = new byte[0];
            if (url.endsWith("html") || url.endsWith("ico")) {
                body = FileIoUtils.loadFileFromClasspath("./templates" + url);
            }
            if (url.endsWith(".css")) {
                body = FileIoUtils.loadFileFromClasspath("./static" + url);
                headers.put("Content-Type", "text/css");
            }
            if (url.endsWith(".js")) {
                body = FileIoUtils.loadFileFromClasspath("./static" + url);
                headers.put("Content-Type", "application/javascript");
            }
            if (url.startsWith("/fonts")) {
                body = FileIoUtils.loadFileFromClasspath("./static" + url);
            }
            response200Header(body.length);
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void forwardBody(String body) {
        byte[] content = body.getBytes();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", content.length + "");
        response200Header(content.length);
        responseBody(content);
    }

    private void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            processHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            processHeaders();
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processHeaders() {
        try {
            for (String key : headers.keySet()) {
                dos.writeBytes(key + ": " + headers.get(key) + " \r\n");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
