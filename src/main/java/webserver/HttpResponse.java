package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.dos = new DataOutputStream(dos);
    }

    public void responseRedirectSetCookie(String redirectUrl, boolean enableCookie) throws IOException, URISyntaxException {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            response302Header(redirectUrl);
            setCookie(enableCookie);
            dos.writeBytes("\r\n");

            responseBody(new byte[]{});
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseRedirect(String redirectUrl) throws IOException, URISyntaxException {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            response302Header(redirectUrl);
            dos.writeBytes("\r\n");

            responseBody(new byte[]{});
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseOk(String path, boolean logined) throws IOException, URISyntaxException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");

        if (path.endsWith(".html")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("templates" + path);
            response200Header(body.length);
            setCookie(logined);
            dos.writeBytes("\r\n");
            responseBody(body);
        } else if (path.endsWith(".css")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("static" + path);
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("\r\n");
            responseBody(body);
        } else if (path.endsWith(".js")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("static" + path);
            dos.writeBytes("Content-Type: text/javascript;charset=utf-8\r\n");
            dos.writeBytes("\r\n");
            responseBody(body);
        } else if (path.contains("/fonts/")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("static" + path);
            dos.writeBytes("Content-Type: font/" + path.substring(path.lastIndexOf(".") + 1) + ";charset=utf-8\r\n");
            dos.writeBytes("\r\n");
            responseBody(body);
        } else if (path.equals("/favicon.ico")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("templates" + path);
            dos.writeBytes("image/x-icon;charset=utf-8\r\n");
            dos.writeBytes("\r\n");
            responseBody(body);
        }
    }

    public void responseOkBody(String body, boolean logined) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        setCookie(logined);
        dos.writeBytes("\r\n");
        responseBody(body.getBytes(StandardCharsets.UTF_8));
    }

    public void setCookie(boolean enable) throws IOException {
        dos.writeBytes("Set-Cookie: logined=" + enable + "; Path=/ \r\n");
    }

    private void response200Header(int lengthOfBodyContent) throws IOException {
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");

    }

    private void response302Header(String redirectUrl) throws IOException {
        dos.writeBytes("Location: " + redirectUrl + "\r\n");
    }

    public void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();

    }
}
