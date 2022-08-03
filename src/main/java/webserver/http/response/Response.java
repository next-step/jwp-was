package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.ContentType;
import webserver.http.Cookie;
import webserver.http.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import static model.Constant.*;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private static final String RESOURCES_TEMPLATES = "./templates";
    private static final String RESOURCES_STATIC = "./static";
    public final static String EXTENSION_SPERATOR = ".";

    private final DataOutputStream out;
    private final ResponseHeader responseHeader;

    public Response(OutputStream out) {
        this.out = new DataOutputStream(out);
        this.responseHeader = new ResponseHeader();
    }

    private String getContentType(String path) {
        return Stream.of(ContentType.values())
                .filter(type -> path.endsWith(type.getExtension()))
                .map(type -> type.getValue())
                .findFirst()
                .orElse(null);
    }

    public void forward(String path) throws IOException, URISyntaxException {
        String prefixConcatPath = getPrefix(path).concat(path);
        byte[] body = FileIoUtils.loadFileFromClasspath(prefixConcatPath);
        logger.debug("ContentType : {}", getContentType(path));
        forward(path, body);
    }

    public void forward(String path, byte[] body) {
        responseHeader.add(CONTENT_LENGTH, String.valueOf(body.length));
        responseHeader.add(CONTENT_TYPE, getContentType(path));
        responseOk();
        setBody(body);
    }

    public void responseOk() {
        try {
            this.out.writeBytes(PROTOCOL_VERSION_ONE_ONE + getStatus(HttpStatus.OK) + LINE_SEPARATOR);
            this.out.writeBytes(LINE_SEPARATOR);
            this.out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String path) {
        try {
            this.out.writeBytes(PROTOCOL_VERSION_ONE_ONE + getStatus(HttpStatus.FOUND) + LINE_SEPARATOR);
            this.out.writeBytes(LOCATION + HEADER_KEY_VALUE_SEPARATOR + path + LINE_SEPARATOR);
            this.out.writeBytes(LINE_SEPARATOR);
            this.out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void setCookie(Cookie cookie) {
        String convertCookieAsString = cookie.getName() + "=" + cookie.getValue() + "; Path=" + cookie.getPath();
        responseHeader.add(SET_COOKIE, convertCookieAsString);
    }

    public void setBody(byte[] body) {
        try {
            this.out.write(body, 0, body.length);
            this.out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getPrefix(String path) {
        String extension = path.substring(path.lastIndexOf(EXTENSION_SPERATOR) + 1);
        if (!ContentType.isStaticExtension(extension)) {
            return RESOURCES_TEMPLATES;
        }
        return RESOURCES_STATIC;
    }

    public DataOutputStream getResponse() {
        return out;
    }

    private String getStatus(HttpStatus httpStatus) {
        return httpStatus.getCode() + " " + httpStatus.getMessage();
    }
}
