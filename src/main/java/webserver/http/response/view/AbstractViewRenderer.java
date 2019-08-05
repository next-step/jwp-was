package webserver.http.response.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

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

    void responseHeader(int lengthOfBodyContent) {

        httpResponse.setHttpStatus(HttpStatus.OK);
        httpResponse.addHeader("Content-Length", String.valueOf(lengthOfBodyContent));
        try {
            this.outputStream.writeBytes(httpResponse.getStatusLine());
            this.outputStream.writeBytes(httpResponse.getHeaders());
            this.outputStream.writeBytes(HttpResponse.CRLF);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    void responseBody(byte[] body) {
        try {
            this.outputStream.write(body, 0, body.length);
            this.outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public abstract void render();
}
