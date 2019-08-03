package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private byte[] body;

    public HttpResponse(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return this.body;
    }

    public void response(DataOutputStream dos, HttpRequest httpRequest) {
        String bodyString = new String(body);

        if (bodyString.startsWith("redirect:")) {
            String redirectPath = String.format("http://%s%s", httpRequest.getHeaders().get("Host"), bodyString.substring(bodyString.indexOf(":") + 1));
            response302Header(dos, redirectPath);
            return;
        }

        response200Header(dos);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String redirectPath) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + redirectPath + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
