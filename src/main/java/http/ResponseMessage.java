package http;

import org.slf4j.Logger;
import utils.FileIoUtils;


import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;

import static org.slf4j.LoggerFactory.getLogger;

public class ResponseMessage {

    private static final Logger logger = getLogger(ResponseMessage.class);
    private final DataOutputStream dos;
    private final Header header = new Header(Collections.emptyList());

    public ResponseMessage(DataOutputStream dos) {
        this.dos = dos;
    }

    public void setHeader(String name, String value) {
        this.header.add(name, value);
    }

    public void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK\r\n");
            writeHeader();
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
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

    public void redirectTo(String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect\r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            writeHeader();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeader() {
        try {
            if (header.size() > 0) {
                dos.writeBytes(this.header.toJoinedString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void response404Header() {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found\r\n");
            dos.writeBytes("Content-Type: text/plain;charset=utf-8\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseResource(String location) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(location);
            response200Header(body.length);
            responseBody(body);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
