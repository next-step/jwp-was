package webserver.http.response;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.ContentType;
import webserver.http.Cookie;
import webserver.http.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static model.Constant.*;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private final DataOutputStream out;
    private ResponseHeader headers;
    private byte[] body;

    public Response(OutputStream out) {
        this.out = new DataOutputStream(out);
    }

    private Map<String, String> getContentType(String path) {
        return new HashMap<>(Stream.of(ContentType.values())
                .filter(type -> path.endsWith(type.getExtension()))
                .map(type -> Map.of("Content-Type", type.getValue()))
                .findFirst()
                .orElse(Collections.emptyMap()));
    }

    public void responseOk() {
        try {
            this.out.writeBytes(PROTOCOL_VERSION_ONE_ONE + getStatus(HttpStatus.OK) + LINE_SEPARATOR);
            this.out.writeBytes(StringUtils.join(this.getHeaders(), HEADER_KEY_VALUE_SEPARATOR));
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
        headers.add(SET_COOKIE, convertCookieAsString);
    }

    public void setBody(byte[] loadData) {
        this.body = loadData;
        headers.add(CONTENT_LENGTH, String.valueOf(this.body.length));
    }

    public DataOutputStream getResponse() {
        return out;
    }

    public ResponseHeader getHeaders() {
        return headers;
    }

    private String getStatus(HttpStatus httpStatus) {
        return httpStatus.getCode() + " " + httpStatus.getMessage();
    }
}
