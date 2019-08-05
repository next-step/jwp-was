package webserver.http.response.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public class StaticResourceViewRenderer extends AbstractViewRenderer {

    private static final Logger logger = LoggerFactory.getLogger(StaticResourceViewRenderer.class);
    private static final String TEMPLATE_RESOURCE_PATH_BASE = "./templates";
    private static final String STATIC_RESOURCE_PATH_BASE = "./static";
    private final DataOutputStream outputStream;

    private String path;

    public StaticResourceViewRenderer(String path, HttpResponse httpResponse) {
        this.outputStream = httpResponse.getOutputStream();
        this.path = path;
    }

    @Override
    public void render() {

        byte[] body = new byte[0];
        try {
            body = FileIoUtils.loadFileFromClasspath(TEMPLATE_RESOURCE_PATH_BASE + path);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        response200Header(outputStream, body.length);
        responseBody(outputStream, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
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
