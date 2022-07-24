package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpProtocolSchema;
import webserver.http.HttpVersion;
import webserver.http.response.HttpResponseStatusLine;
import webserver.http.response.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    public static void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            new HttpResponseStatusLine(HttpProtocolSchema.of(HttpVersion.ONE_POINT_ONE), HttpStatus.OK);

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
