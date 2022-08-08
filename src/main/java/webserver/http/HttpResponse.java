package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.WatcherOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private WatcherOutputStream dos;

    private Headers headers = new Headers();


    public HttpResponse(OutputStream out) {
        dos = new WatcherOutputStream(out);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public byte[] forward(String url) {
        byte[] body = ResourceHandler.handle(url, headers);
        response200Header();
        responseBody(body);
        return body;
    }

    public void forwardBody(String body) {
        byte[] contents = body.getBytes();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", contents.length + "");
        response200Header();
        responseBody(contents);
    }

    private void response200Header() {
        try {
            dos.write("HTTP/1.1 200 OK \r\n");
            processHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body);
            dos.write("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String redirectUrl) {
        try {
            dos.write("HTTP/1.1 302 Found \r\n");
            processHeaders();
            dos.write("Location: " + redirectUrl + " \r\n");
            dos.write("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processHeaders() {
        try {
            Set<String> keys = headers.getKeySet();
            for (String key : keys) {
                dos.write(key + ": " + headers.getHeader(key) + " \r\n");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public Headers getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.getHeader(key);
    }

    public byte[] getBytes() {
        return dos.getData();
    }
}
