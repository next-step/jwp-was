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
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static model.Constant.*;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private static final String RESOURCES_TEMPLATES = "./templates";
    private static final String RESOURCES_STATIC = "./static";
    public final static String EXTENSION_SPERATOR = ".";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_KEY_VALUE_SEPARATOR = ": ";
    public static final String LINE_SEPARATOR = "\r\n";
    public static final String HEADER_SEPARATOR = " ";


    private DataOutputStream out;
    private ResponseLine responseLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;
    private Cookie cookie;

    public HttpResponse(ResponseLine responseLine, ResponseHeader responseHeader) {
        this.responseLine = responseLine;
        this.responseHeader = responseHeader;
        this.responseBody = new ResponseBody(new byte[0]);
        this.cookie = new Cookie(Collections.emptyMap());
    }

    public HttpResponse(ResponseLine responseLine, ResponseHeader responseHeader, Cookie cookie) {
        this.responseLine = responseLine;
        this.responseHeader = responseHeader;
        this.responseBody = new ResponseBody(new byte[0]);
        this.cookie = cookie;
    }

    public HttpResponse(ResponseLine responseLine, ResponseHeader responseHeader, ResponseBody responseBody) {
        this.responseLine = responseLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
        this.cookie = new Cookie(Collections.emptyMap());
    }

    public HttpResponse(OutputStream outputStream) {

    }

    public static HttpResponse sendRedirect(String path, Map<String, String> cookieMap) {
        return new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.FOUND), new ResponseHeader(LOCATION, path), new Cookie(cookieMap));
    }

    public static HttpResponse sendRedirect(String path) {
        return new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.FOUND), new ResponseHeader(LOCATION, path));
    }

    public static HttpResponse forward(String path, byte[] responseBody) {
        return new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.OK), new ResponseHeader(LOCATION, addPrefixPath(path)), new ResponseBody(responseBody));
    }

    public static HttpResponse forward(String path) throws IOException, URISyntaxException {
        String prefixConcatPath = addPrefixPath(path);
        byte[] body = FileIoUtils.loadFileFromClasspath(prefixConcatPath);
        return new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.OK), new ResponseHeader(), new ResponseBody(body));
    }

    public static HttpResponse notfound() {
        return new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.NOT_FOUND), new ResponseHeader());

    }

    public void process(DataOutputStream out) {
        this.out = out;

        try {
            isResponseBody(responseBody, responseHeader);
            isExistCookie(cookie);

            writeResponseLine(responseLine);
            writeResponseHeader(responseHeader);
            writeResponseBody(responseBody);
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void isExistCookie(Cookie cookie) {
        if (!cookie.getCookie().isEmpty()) {
            responseHeader.add(SET_COOKIE, cookie.getCookie(SET_COOKIE));
        }
    }

    private void isResponseBody(ResponseBody responseBody, ResponseHeader responseHeader) {
        if (responseBody.getContentLength() > 0) {
            responseHeader.add(CONTENT_LENGTH, responseBody.getContentLength());
        }
    }

    private void writeResponseBody(ResponseBody responseBody) throws IOException {
        out.writeBytes(LINE_SEPARATOR);
        out.write(responseBody.getResponseBody(), 0, responseBody.getContentLength());
    }

    private void writeResponseHeader(ResponseHeader responseHeader) throws IOException {
        out.writeBytes(CONTENT_TYPE + HEADER_KEY_VALUE_SEPARATOR + responseHeader.getHeader(LOCATION) + LINE_SEPARATOR);
        for (Map.Entry<String, Object> entry : responseHeader.getHeaders().entrySet()) {
            out.writeBytes(entry.getKey() + HEADER_KEY_VALUE_SEPARATOR + entry.getValue() + LINE_SEPARATOR);
            logger.debug("responseHeader : {}", entry.getKey() + HEADER_KEY_VALUE_SEPARATOR + entry.getValue() + LINE_SEPARATOR);
        }
    }

    private void writeResponseLine(ResponseLine responseLine) throws IOException {
        out.writeBytes(responseLine.getProtocolAndVersion() + getStatus(responseLine) + LINE_SEPARATOR);
    }

    private static String addPrefixPath(String path) {
        String extension = path.substring(path.lastIndexOf(EXTENSION_SPERATOR) + 1);
        if (!ContentType.isStaticExtension(extension)) {
            return RESOURCES_TEMPLATES.concat(path);
        }
        return RESOURCES_STATIC.concat(path);
    }

    private String getStatus(ResponseLine responseLine) {
        return responseLine.getHttpStatus().getCode() + HEADER_SEPARATOR + responseLine.getHttpStatus().getMessage();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponse that = (HttpResponse) o;
        return Objects.equals(out, that.out) && Objects.equals(responseLine, that.responseLine) && Objects.equals(responseHeader, that.responseHeader) && Objects.equals(responseBody, that.responseBody) && Objects.equals(cookie, that.cookie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(out, responseLine, responseHeader, responseBody, cookie);
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "out=" + out +
                ", responseLine=" + responseLine +
                ", responseHeader=" + responseHeader +
                ", responseBody=" + responseBody +
                ", cookie=" + cookie +
                '}';
    }
}
