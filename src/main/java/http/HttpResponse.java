package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private String resourcePath = "";

    private DataOutputStream dos = null;
    private HttpHeaders headers = new HttpHeaders();

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public void forward(String url) {
        byte[] body = new byte[0];
        try {
            if (url.endsWith(".css")) {
                resourcePath = ContentType.CSS.resourcePath;
                headers.put("Content-Type", ContentType.CSS.mimeType);
            }else if (url.endsWith(".ttf") || url.endsWith(".woff")) {
                resourcePath = ContentType.FONT.resourcePath;
                headers.put("Content-Type", ContentType.FONT.mimeType);
            } else if (url.endsWith(".js")) {
                resourcePath = ContentType.JS.resourcePath;
                headers.put("Content-Type", ContentType.JS.mimeType);
            } else {
                resourcePath = ContentType.HTML.resourcePath;
                headers.put("Content-Type", "text/html");
            }
            body = getBody(url);
            headers.put("Content-Length", body.length + "");
            response200Header();
            responseBody(body);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public byte[] getBody(String url) throws IOException {
        byte[] body = new byte[0];
        try {
            body = FileIoUtils.loadFileFromClasspath(resourcePath + url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return body;
    }

    public void response200Header() {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes(headers.makeResponseHeader());
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
            dos.writeBytes(headers.makeResponseHeader());
            dos.writeBytes("\r\n");
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

    public void responseTemplateBody(byte[] body) {
        headers.put("Content-Length", body.length + "");
        response200Header();
        responseBody(body);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

}
