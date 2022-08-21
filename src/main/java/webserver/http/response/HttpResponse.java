package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.ContentType;
import webserver.http.Cookie;
import webserver.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static model.Constant.LOCATION;
import static model.Constant.PROTOCOL_VERSION_ONE_ONE;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private static final String RESOURCES_TEMPLATES = "./templates";
    private static final String RESOURCES_STATIC = "./static";
    public final static String EXTENSION_SPERATOR = ".";

    private ResponseLine responseLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;
    private Cookie cookie;

    public HttpResponse(ResponseLine responseLine, ResponseHeader responseHeader) {
        this(responseLine, responseHeader, new ResponseBody(new byte[0]), new Cookie(Collections.emptyMap()));
    }

    public HttpResponse(ResponseLine responseLine, ResponseHeader responseHeader, Cookie cookie) {
        this(responseLine, responseHeader, new ResponseBody(new byte[0]), cookie);
    }

    public HttpResponse(ResponseLine responseLine, ResponseHeader responseHeader, ResponseBody responseBody) {
        this.responseLine = responseLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
        this.cookie = new Cookie(new HashMap<>());
    }

    public HttpResponse(ResponseLine responseLine, ResponseHeader responseHeader, ResponseBody responseBody, Cookie cookie) {
        this.responseLine = responseLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
        this.cookie = cookie;
    }

    public static HttpResponse sendRedirect(String path, Map<String, Object> cookieMap) {
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

    private static String addPrefixPath(String path) {
        String extension = path.substring(path.lastIndexOf(EXTENSION_SPERATOR) + 1);
        if (!ContentType.isStaticExtension(extension)) {
            return RESOURCES_TEMPLATES.concat(path);
        }
        return RESOURCES_STATIC.concat(path);
    }

    public int getContentLength() {
        return responseBody.getContentLength();
    }

    public void setHeader(String key, Object value) {
        responseHeader.add(key, value);
    }

    public boolean isExistCookie() {
        return cookie.getCookie().isEmpty();
    }

    public Object getCookie(String key) {
        return cookie.getCookie(key);
    }

    public ResponseLine getResponseLine() {
        return responseLine;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    public void addCookie(String key, String value) {
        cookie.setCookie(key, value);
    }

    public Cookie getCookie() {
        return cookie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponse that = (HttpResponse) o;
        return Objects.equals(responseLine, that.responseLine) && Objects.equals(responseHeader, that.responseHeader) && Objects.equals(responseBody, that.responseBody) && Objects.equals(cookie, that.cookie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseLine, responseHeader, responseBody, cookie);
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "responseLine=" + responseLine +
                ", responseHeader=" + responseHeader +
                ", responseBody=" + responseBody +
                ", cookie=" + cookie +
                '}';
    }
}
