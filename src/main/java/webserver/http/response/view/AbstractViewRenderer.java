package webserver.http.response.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public abstract class AbstractViewRenderer implements ViewRenderer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractViewRenderer.class);
    final HttpResponse httpResponse;
    private final DataOutputStream outputStream;

    public AbstractViewRenderer(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
        this.outputStream = httpResponse.getOutputStream();
    }

    void writeHeader(int lengthOfBodyContent) {
        try {
            httpResponse.addHeader("Content-Length", String.valueOf(lengthOfBodyContent));
            outputStream.writeBytes(httpResponse.getStatusLine());
            outputStream.writeBytes(httpResponse.getHeaders());
            outputStream.writeBytes(HttpResponse.CRLF);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    void writeBody(byte[] body) {
        try {
            outputStream.write(body, 0, body.length);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    void flush() {
        try {
            outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public abstract void render();
}
