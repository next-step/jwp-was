package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Set;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;

    private Headers headers = new Headers();

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void forward(String url) {
        try {
            byte[] body = null;

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
            if (url.endsWith("html") || url.endsWith("ico")) {
                body = FileIoUtils.loadFileFromClasspath("./templates" + url);
            }

            response200Header(body.length);
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void forwardBody(String body) {
        byte[] contents = body.getBytes();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", contents.length + "");
        response200Header(contents.length);
        responseBody(contents);
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

    public void sendRedirect(String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            processHeaders();
            dos.writeBytes("Location: " + redirectUrl + " \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseLoginSuccess() {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Set-Cookie: logined=true \r\n");
            dos.writeBytes("Location: / \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processHeaders() {
        try {
            Set<String> keys = headers.getKeySet();
            for (String key : keys) {
                dos.writeBytes(key + ": " + headers.getHeader(key) + " \r\n");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
