package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private byte[] body;
    private Cookie cookie;
    private Model model;
    private int statusCode;
    private String redirectPath;

    public HttpResponse() {
        this.cookie = new Cookie();
        this.model = new Model();
        this.statusCode = 200;
    }

    public HttpResponse(int statusCode, byte[] body) {
        this.body = body;
        this.cookie = new Cookie();
        this.model = new Model();
        this.statusCode = statusCode;
    }

    public byte[] getBody() {
        return this.body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Model getModel() {
        return model;
    }

    public Cookie getCookie() {
        return cookie;
    }

    private void setCookies(DataOutputStream dos) {
        if (cookie.isEmpty()) return;

        String setCookieFormat = "Set-Cookie: %s=%s; Path=/\r\n";
        cookie.keySet().forEach(key -> {
            try {
                dos.writeBytes(String.format(setCookieFormat, key, cookie.get(key)));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        });
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public void responseByStatus(DataOutputStream dos) {
        if (statusCode == 200) {
            response200Header(dos);
            responseBody(dos);
        }

        if (statusCode == 302) {
            response302Header(dos);
        }
    }

    private void response200Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            setCookies(dos);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + redirectPath + "\r\n");
            setCookies(dos);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
