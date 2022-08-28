package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.ContentType;
import webserver.http.Cookie;
import webserver.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static model.Constant.LOCATION;
import static model.Constant.PROTOCOL_VERSION_ONE_ONE;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private static final String RESOURCES_TEMPLATES = "./templates";
    private static final String RESOURCES_STATIC = "./static";

    private ResponseLine responseLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;
    private Cookie cookie;

    public HttpResponse(ResponseLine responseLine, ResponseHeader responseHeader, ResponseBody responseBody, Cookie cookie) {
        this.responseLine = responseLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
        this.cookie = cookie;
    }

    public HttpResponse(ResponseLine responseLine, ResponseHeader responseHeader, ResponseBody responseBody) {
        this.responseLine = responseLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }

    public HttpResponse(ResponseLine responseLine, ResponseHeader responseHeader) {
        this(responseLine, responseHeader, new ResponseBody(new byte[0]));
    }

    public HttpResponse(ResponseLine responseLine, ResponseHeader responseHeader, Cookie cookie) {
        this(responseLine, responseHeader, new ResponseBody(new byte[0]), cookie);
    }

    public static HttpResponse sendRedirect(String path, Cookie cookie) {
        return new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.FOUND), new ResponseHeader(LOCATION, path), cookie);
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
        return new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.OK), new ResponseHeader(LOCATION, path), new ResponseBody(body));
    }

    public static HttpResponse notfound() {
        return new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.NOT_FOUND), new ResponseHeader());

    }

    private static String addPrefixPath(String path) {
        if (!ContentType.isStaticExtension(path)) {
            return RESOURCES_TEMPLATES.concat(path);
        }
        return RESOURCES_STATIC.concat(path);
    }

    public int getContentLength() {
        return responseBody.getContentLength();
    }

    public void setHeader(String key, Object value) {
        responseHeader.setHeader(key, value);
    }

    public boolean isExistCookie() {
        return cookie == null;
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
