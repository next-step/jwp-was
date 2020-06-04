package webserver.writer;

import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DefaultHttpResponseWriter implements HttpResponseWriter {
    private static final Logger logger = LoggerFactory.getLogger(DefaultHttpResponseWriter.class);

    @Override
    public void write(final OutputStream outputStream, final HttpResponse httpResponse) {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        response200Header(dataOutputStream, httpResponse.getBodyLength());
        responseBody(dataOutputStream, httpResponse.getBody());
    }

    private void response200Header(DataOutputStream dataOutputStream, int lengthOfBodyContent) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dataOutputStream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dataOutputStream.writeBytes("\r\n");
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
