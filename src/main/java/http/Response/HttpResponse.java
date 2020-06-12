package http.Response;

import http.HttpHeaderName;
import http.HttpHeaders;
import http.view.View;
import java.io.IOException;
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

    private final HttpHeaders headers = new HttpHeaders();
    String statusLine;
    private byte[] body;
    private View view;

    abstract byte[] readContents(String path);

    abstract String getFilePath(String path);

    public abstract boolean isMyResponse(String path);

    public void setView(View view) {
        this.view = view;
    }

    public void response(String path) {
        this.body = readContents(path);
        this.statusLine = setStatusLine();
        addHeaders(HttpHeaderName.CONTENT_LENGTH.toString(), String.valueOf(body.length));
        view.render(assembleHeader(statusLine, headers), body);
    }

    private String assembleHeader(String statusLine, HttpHeaders headers) {
        return statusLine + headers.toString();
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

    public void redirect(String redirectLocation) {
        addHeaders(HttpHeaderName.LOCATION.toString(), redirectLocation);
        view.render(assembleHeader(REDIRECT_STATUS_LINE, headers), new byte[0]);
    }

}
