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

import static model.Constant.LOCATION;
import static model.Constant.PROTOCOL_VERSION_ONE_ONE;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private static final String RESOURCES_TEMPLATES = "./templates";
    private static final String RESOURCES_STATIC = "./static";
    public final static String EXTENSION_SPERATOR = ".";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String CONTENT_LENGTH = "Content-Length";
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

    public static HttpResponse forward(String path, String responseBody) throws IOException, URISyntaxException {
        return new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.FOUND), new ResponseHeader(LOCATION, addPrefixPath(path)), new ResponseBody(FileIoUtils.loadFileFromClasspath(responseBody)));
    }

    public static HttpResponse sendRedirect(String path, Map<String, String> cookieMap) {
        return new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.FOUND), new ResponseHeader(LOCATION, addPrefixPath(path)), new Cookie(cookieMap));
    }

    public static HttpResponse sendRedirect(String path) {
        return new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.FOUND), new ResponseHeader(LOCATION, addPrefixPath(path)));
    }

    public void process(OutputStream out) {
        this.out = new DataOutputStream(out);

        try {
            isExistResponseBody(responseBody, responseHeader);

            writeResponseLine(responseLine);
            writeResponseHeader(responseHeader);
            writeResponseBody(responseBody);
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponseBody(ResponseBody responseBody) throws IOException {
        out.writeBytes(LINE_SEPARATOR);
        out.write(responseBody.getResponseBody(), 0, responseBody.getContentLength());
    }

    private void isExistResponseBody(ResponseBody responseBody, ResponseHeader responseHeader) {
        if (responseBody.getContentLength() > 0) {
            responseHeader.add(CONTENT_LENGTH, responseBody.getContentLength());
        }
    }

    private void writeResponseHeader(ResponseHeader responseHeader) throws IOException {
        out.writeBytes(responseHeader.toString() + LINE_SEPARATOR);
    }

    private void writeResponseLine(ResponseLine responseLine) throws IOException {
        out.writeBytes(responseLine.getProtocolAndVersion() + getStatus(responseLine) + LINE_SEPARATOR);
    }

    public void setCookie(Cookie cookie) {
        String convertCookieAsString = cookie.getCookieAsString();
        responseHeader.add(SET_COOKIE, convertCookieAsString);
    }

    private static String addPrefixPath(String path) {
        String extension = path.substring(path.lastIndexOf(EXTENSION_SPERATOR) + 1);
        if (!ContentType.isStaticExtension(extension)) {
            return RESOURCES_TEMPLATES + path;
        }
        return RESOURCES_STATIC + path;
    }

    private String getStatus(ResponseLine responseLine) {
        return responseLine.getHttpStatus().getCode() + HEADER_SEPARATOR + responseLine.getHttpStatus().getMessage();
    }
}
