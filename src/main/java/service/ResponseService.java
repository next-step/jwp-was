package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseService {
    private static final Logger logger = LoggerFactory.getLogger(ResponseService.class);

    public static void makeResponseHeader(DataOutputStream dataOutputStream, byte[] body) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dataOutputStream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            if (body == null) {
                dataOutputStream.writeBytes("\r\n");
                return;
            }

            dataOutputStream.writeBytes("Content-Length: " + body.length + "\r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void makeResponseBody(DataOutputStream dataOutputStream, byte[] body) {
        try {
            if (body == null) {
                dataOutputStream.flush();
                return;
            }
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
