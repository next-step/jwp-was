package webserver.http.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class ForwardController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(ForwardController.class);

    private final byte[] body;

    public ForwardController(byte[] body) {
        this.body = body;
    }

    @Override
    public void execute(RequestLine requestLine, DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
