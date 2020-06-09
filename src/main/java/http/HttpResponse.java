package http;

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
public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private static final String REDIRECT_STATUS_LINE = "HTTP/1.1 302 Found \r\n";
    private static final String OK_STATUS_LINE = "HTTP/1.1 200 OK \r\n";
    private static final String CRLF = "\r\n";

    private static final String CSS_RESOURCE_PREFIX = "/css";
    private static final String FONT_RESOURCE_PREFIX = "/fonts";
    private static final String IMAGES_RESOURCE_PREFIX = "/images";
    private static final String JS_RESOURCE_PREFIX = "/js";

    private static final String STATIC_CONTENT_PATH = "./static";
    private static final String TEMPLATE_CONTENT_PATH = "./templates";

    private static final String CSS_CONTENT_TYPE = "text/css";
    private static final String FONT_CONTENT_TYPE_PREFIX = "font/";
    private static final String IMAGES_CONTENT_TYPE_PREFIX = "image/";
    private static final String JS_CONTENT_TYPE = "application/javascript";
    private static final String HTML_CONTENT_TYPE = "text/html;charset=utf-8";

    private final DataOutputStream dos;
    private HttpHeaders headers = new HttpHeaders();

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void redirect(String redirectLocation) {
        try {
            dos.writeBytes(REDIRECT_STATUS_LINE);
            addHeaders(HttpHeaderName.LOCATION.toString(), redirectLocation);
            writeHeaders();
            dos.writeBytes(CRLF);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response(String path) {
        try {
            byte[] body = readContents(path);
            addHeaders(HttpHeaderName.CONTENT_LENGTH.toString(), String.valueOf(body.length));
            response200Header();
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] readContents(String path) throws IOException, URISyntaxException {
        if (path.startsWith(CSS_RESOURCE_PREFIX)) {
            addHeaders(HttpHeaderName.CONTENT_TYPE.toString(), CSS_CONTENT_TYPE);
            return readStaticContents(path);
        }
        if (path.startsWith(FONT_RESOURCE_PREFIX)) {
            addHeaders(HttpHeaderName.CONTENT_TYPE.toString(), FONT_CONTENT_TYPE_PREFIX + getFileExtention(path));
            return readStaticContents(path);
        }
        if (path.startsWith(IMAGES_RESOURCE_PREFIX)) {
            addHeaders(HttpHeaderName.CONTENT_TYPE.toString(), IMAGES_CONTENT_TYPE_PREFIX + getFileExtention(path));
            return readStaticContents(path);
        }
        if (path.startsWith(JS_RESOURCE_PREFIX)) {
            addHeaders(HttpHeaderName.CONTENT_TYPE.toString(), JS_CONTENT_TYPE);
            return readStaticContents(path);
        }

        addHeaders(HttpHeaderName.CONTENT_TYPE.toString(), HTML_CONTENT_TYPE);
        return FileIoUtils.loadFileFromClasspath(TEMPLATE_CONTENT_PATH + path);
    }

    private byte[] readStaticContents(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(STATIC_CONTENT_PATH + path);
    }

    public void addHeaders(String key, String value) {
        headers.addHeader(key, value);
    }

    public void responseBody(String bodyString) {
        byte[] body = bodyString.getBytes();
        addHeaders(HttpHeaderName.CONTENT_TYPE.toString(), HTML_CONTENT_TYPE);
        addHeaders(HttpHeaderName.CONTENT_LENGTH.toString(), String.valueOf(body.length));
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
            dos.writeBytes(OK_STATUS_LINE);
            writeHeaders();
            dos.writeBytes(CRLF);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeaders() {
        try {
            dos.writeBytes(headers.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getFileExtention(String path) {
        int position = path.lastIndexOf('.');
        return path.substring(position + 1);
    }
}
