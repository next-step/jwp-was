package webserver.http.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static utils.FileIoUtils.loadFileFromClasspath;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final DataOutputStream dos;
    private final Map<String, String> headers;

    public HttpResponse(OutputStream outputStream) {
        dos = new DataOutputStream(outputStream);
        this.headers = new HashMap<>();
    }

    public void addHeader(String key, String value){
        headers.put(key, value);
    }

    public void staticForward(String path) {
        try {
            byte[] body = loadFileFromClasspath(path);
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            processHeaders();
            dos.writeBytes("\r\n");
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void forward(String path) {
        try {
            byte[] body = loadFileFromClasspath(path);
            response200Header(body.length);
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void forwardBody(String path) {
        try {
            byte[] body = path.getBytes();
            response200Header(body.length);
            responseBody(body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(int bodyLength) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + bodyLength + "\r\n");
        dos.writeBytes("\r\n");
    }

    public void sendRedirect(String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            processHeaders();
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processHeaders() {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            try {
                dos.writeBytes(entry.getKey() + ": " + entry.getValue() + " \r\n");
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
