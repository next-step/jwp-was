package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dos;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void redirect(String redirectLocation) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + redirectLocation + "\r\n");
            processHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response(String path) {
        try {
            byte[] body;
            if (path.startsWith("/css")) {
                addHeaders("Content-Type", "text/css");
                body = FileIoUtils.loadFileFromClasspath("./static" + path);
            } else if (path.startsWith("/fonts")) {
                addHeaders("Content-Type", "font/" + getFileExtention(path));
                body = FileIoUtils.loadFileFromClasspath("./static" + path);
            } else if (path.startsWith("/images")) {
                addHeaders("Content-Type", "image/" + getFileExtention(path));
                body = FileIoUtils.loadFileFromClasspath("./static" + path);
            } else if (path.startsWith("/js")) {
                addHeaders("Content-Type", "application/javascript");
                body = FileIoUtils.loadFileFromClasspath("./static" + path);
            } else {
                addHeaders("Content-Type", "text/html;charset=utf-8");
                body = FileIoUtils.loadFileFromClasspath("./templates" + path);
            }
            addHeaders("Content-Length", String.valueOf(body.length));
            response200Header();
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void addHeaders(String key, String value) {
        headers.put(key, value);
    }

    public void responseBody(String userList) {
        byte[] body = userList.getBytes();
        addHeaders("Content-Type", "text/html;charset=utf-8");
        addHeaders("Content-Length", String.valueOf(body.length));
        response200Header();
        responseBody(body);
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    private void response200Header() {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            processHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processHeaders() {
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            try {
                dos.writeBytes(key + ": " + headers.get(key) + "\r\n");
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private String getFileExtention(String path) {
        int position = path.lastIndexOf('.');
        return path.substring(position + 1);
    }
}
