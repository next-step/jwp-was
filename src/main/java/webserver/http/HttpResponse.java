package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DefaultPageUtils;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Set;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;
    private ResponseHeader headers;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.headers = new ResponseHeader();
    }

    public void addHeader(String key, String value) {
        headers.addHeader(key, value);
    }

    public void updateStatus(HttpStatus status) {
        headers.updateStatus(status);
    }

    public HttpStatus getStatus() {
        return headers.getStatus();
    }

    public void forward(String path) throws IOException, URISyntaxException {
        HttpContentType contentType = HttpContentType.of(getFileExtension(path));
        byte[] body = FileIoUtils.loadFileFromClasspath(contentType.getResourcePath() + path);
        addHeader("Content-Type", contentType.getValue());
        addHeader("Content-Length", String.valueOf(body.length));
        responseHeader();
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
        addHeader("Content-Length", String.valueOf(contents.length));
        responseHeader();
        responseBody(contents);
    }

    public void responseHeader() {
        try {
            processStatusLine();
            processHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String location) {
        updateStatus(HttpStatus.FOUND);
        addHeader("Location", location);
        responseHeader();
    }

    private void processStatusLine() throws IOException {
        dos.writeBytes("HTTP/1.1 " + headers.getStatusMessage() + " \r\n");
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void processHeaders() throws IOException {
        Set<String> keys = headers.getHeaderKeys();
        for (String key : keys) {
            dos.writeBytes(key + ": " + headers.getHeader(key) + "\r\n");
        }
    }

    public void forwardError(HttpStatus httpStatus) {
        updateStatus(httpStatus);
        String errorPage = DefaultPageUtils.makeErrorPage(httpStatus);
        byte[] contents = errorPage.getBytes();
        addHeader("Content-Length", String.valueOf(contents.length));
        responseHeader();
        responseBody(contents);
    }
}
