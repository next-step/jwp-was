package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

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

    public void responseOk(String path, boolean isLogined) throws IOException, URISyntaxException {
        final byte[] body = FileIoUtils.loadFileFromClasspath("templates" + path);

        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        response200Header(body.length);

        if (isLogined) {
            logger.debug("enableCookie is true");
            setCookie(true);
        }

        dos.writeBytes("\r\n");

        responseBody(body);
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
