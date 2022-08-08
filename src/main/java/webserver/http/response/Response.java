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
import java.util.Map;
import java.util.stream.Stream;

import static model.Constant.*;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private static final String RESOURCES_TEMPLATES = "./templates";
    private static final String RESOURCES_STATIC = "./static";

    public final static String EXTENSION_SPERATOR = ".";
    public final static String MAP_KEY_VALUE_SEPARATOR = "=";

    public static final String SET_COOKIE = "Set-Cookie";
    public static final String CONTENT_LENGTH = "Content-Length";

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
        forward(path, body);
    }

    public void forward(String path, byte[] body) {
        responseHeader.add(CONTENT_TYPE, getContentType(path));
        responseOk();
        responseHeader.add(CONTENT_LENGTH, String.valueOf(body.length));
        setBody(body);
    }

    public void responseOk() {
        try {
            out.writeBytes(PROTOCOL_VERSION_ONE_ONE + HttpStatus.getStatus(HttpStatus.OK) + LINE_SEPARATOR);
            applyHeader();
            out.writeBytes(LINE_SEPARATOR);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String path) {
        try {
            out.writeBytes(PROTOCOL_VERSION_ONE_ONE + HttpStatus.getStatus(HttpStatus.FOUND) + LINE_SEPARATOR);
            out.writeBytes(LOCATION + HEADER_KEY_VALUE_SEPARATOR + path + LINE_SEPARATOR);
            applyHeader();
            out.writeBytes(LINE_SEPARATOR);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void setCookie(Cookie cookie) {
        String convertCookieAsString = cookie.getName() + MAP_KEY_VALUE_SEPARATOR + cookie.getValue() + "; Path=" + cookie.getPath();
        responseHeader.add(SET_COOKIE, convertCookieAsString);
    }

    public String getResponseHeader() {
        return responseHeader.getHeaders().get("");
    }

    public void setBody(byte[] body) {
        try {
            out.write(body, 0, body.length);
            out.flush();
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

    private void applyHeader() throws IOException {
        for (final Map.Entry<String, String> entry : responseHeader.getHeaders().entrySet()) {
            out.writeBytes(entry.getKey() + HEADER_KEY_VALUE_SEPARATOR + entry.getValue() + LINE_SEPARATOR);
        }
    }
}
