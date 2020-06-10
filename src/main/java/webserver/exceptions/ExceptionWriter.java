package webserver.exceptions;

import http.response.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExceptionWriter {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionWriter.class);

    public static void write(OutputStream out, ErrorMessage errorMessage) {
        final DataOutputStream dos = new DataOutputStream(out);
        final StatusCode statusCode = StatusCode.INTERNAL_SERVER_ERROR;
        final byte[] body = errorMessage.getMessage().getBytes();
        final int contentLength = body.length;

        try {
            dos.writeBytes("HTTP/1.1 " + statusCode.getCode() + " " + statusCode.getMessage() + "\r\n");
            dos.writeBytes("Content-Type: text/plain\r\n");
            dos.writeBytes("Content-Length: " + contentLength + "\r\n");
            dos.writeBytes("\r\n");
            dos.write(body, 0, contentLength);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
