package http.Response;

import http.HttpHeaderName;
import http.HttpHeaders;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public abstract class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private static final String REDIRECT_STATUS_LINE = "HTTP/1.1 302 Found \r\n";
    private static final String OK_STATUS_LINE = "HTTP/1.1 200 OK \r\n";

    private DataOutputStream dos;
    String statusLine;
    private HttpHeaders headers = new HttpHeaders();
    private byte[] body;

    abstract byte[] readContents(String path);

    abstract String getFilePath(String path);

    public abstract boolean isMyResponse(String path);

    public void setDataOutputStream(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void response(String path) {
        this.body = readContents(path);
        this.statusLine = setStatusLine();
        addHeaders(HttpHeaderName.CONTENT_LENGTH.toString(), String.valueOf(body.length));
        responseHeader();
        responseBody(body);
    }

    String setStatusLine() {
        return OK_STATUS_LINE;
    }

    byte[] readBytes(String path) {
        try {
            return FileIoUtils.loadFileFromClasspath(getFilePath(path));
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            return new byte[0];
        }
    }

    String getFileExtention(String path) {
        int position = path.lastIndexOf('.');
        return path.substring(position + 1);
    }

    public void addHeaders(String key, String value) {
        headers.addHeader(key, value);
    }

    private void responseHeader() {
        try {
            dos.writeBytes(statusLine);
            dos.writeBytes(headers.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void redirect(String redirectLocation) {
        try {
            dos.writeBytes(REDIRECT_STATUS_LINE);
            addHeaders(HttpHeaderName.LOCATION.toString(), redirectLocation);
            dos.writeBytes(headers.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
