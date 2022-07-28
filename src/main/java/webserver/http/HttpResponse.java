package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public void forward(String path) throws IOException, URISyntaxException {
        HttpContentType contentType = HttpContentType.of(getFileExtension(path));
        byte[] body = FileIoUtils.loadFileFromClasspath(contentType.getResourcePath() + path);
        addHeader("Content-Type", contentType.getValue());
        response200Header(body.length);
        responseBody(body);
    }

    private String getFileExtension(String path) {
        String[] split = path.split("\\.");
        String fileExtention = split[split.length - 1];
        return fileExtention;
    }

    public void forwardBody(String body) {
        byte[] contents = body.getBytes();
        addHeader("Content-Type", "text/html;charset=utf-8");
        response200Header(contents.length);
        responseBody(contents);
    }

    public void response200Header(int contentLength) {
        try {
            dos.writeBytes("HTTP/1.1 " + getStatus(HttpStatus.OK) + " \r\n");
            processHeaders();
            dos.writeBytes("Content-Length: " + contentLength + "\r\n");
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

    public void sendRedirect(String location) {
        try {
            dos.writeBytes("HTTP/1.1 " + getStatus(HttpStatus.FOUND) + " \r\n");
            processHeaders();
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getStatus(HttpStatus httpStatus) {
        return httpStatus.toString();
    }

    public void processHeaders() {
        try {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                dos.writeBytes(key + ": " + headers.get(key) + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void forwardError(HttpStatus httpStatus) {
        String errorPage = makeErrorPage(httpStatus);
        byte[] contents = errorPage.getBytes();
        responseErrorHeader(httpStatus, contents.length);
        responseBody(contents);
    }

    private String makeErrorPage(HttpStatus httpStatus) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html>");
        sb.append("<head></head>");
        sb.append("<body><h1>Whitelabel Error Page</h1>");
        sb.append("<div>");
        sb.append(httpStatus.toString());
        sb.append("</div>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    public void responseErrorHeader(HttpStatus httpStatus, int contentLength) {
        try {
            dos.writeBytes("HTTP/1.1 " + getStatus(httpStatus) + " \r\n");
            processHeaders();
            dos.writeBytes("Content-Length: " + contentLength + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
