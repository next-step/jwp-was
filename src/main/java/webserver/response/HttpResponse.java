package webserver.response;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import webserver.request.Cookie;
import webserver.request.Cookies;
import webserver.request.HttpRequest;
import webserver.request.enums.HttpVersion;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by hspark on 2019-08-05.
 */
public class HttpResponse {
    private static final String CRLF = "\r\n";
    private static final String CONTENT_TYPE = "Content-Type: ";
    private static final String CONTENT_LENGTH = "Content-Length: ";
    private static final String SET_COOKIE = "Set-Cookie: ";
    private static final String EQUAL_STR = "=";
    private static final String SEMICOLON_STR = ";";

    private HttpStatus httpStatus;
    private HttpVersion httpVersion;
    private ResponseHeaders responseHeaders = new ResponseHeaders();
    private Cookies cookies = new Cookies();

    private byte[] body = new byte[0];

    public HttpResponse(HttpRequest httpRequest) {
        this.httpVersion = httpRequest.getHttpVersion();
        String contentType = httpRequest.getRequestHeaders().getContentTypeByAccept();
        this.responseHeaders.addHeader(CONTENT_TYPE, contentType);
    }

    public String getHeader(String name) {
        return responseHeaders.getHeader(name);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public byte[] getBody() {
        return body;
    }

    public ResponseHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public String getStatusLineString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.httpVersion.getHeaderValue())
                .append(StringUtils.SPACE).append(httpStatus.getStatusCode())
                .append(StringUtils.SPACE).append(httpStatus.name());
        return stringBuilder.toString();
    }

    public String getHeaderString() {
        StringBuilder stringBuilder = new StringBuilder();
        appendHeaders(stringBuilder);
        if (cookies.hasCookie()) {
            appendCookie(stringBuilder);
        }
        return stringBuilder.append(CRLF).toString();
    }


    public void response(DataOutputStream dos) throws IOException {
        String statusLine = getStatusLineString();
        String headers = getHeaderString();

        dos.writeBytes(statusLine);
        dos.writeBytes(headers);
        dos.write(getBody(), 0, getBody().length);
        dos.flush();
    }

    private void appendHeaders(StringBuilder stringBuilder) {
        responseHeaders.getHeaders()
                .entrySet()
                .forEach(it -> stringBuilder.append(it.getKey() + " : " + it.getValue()).append(CRLF));
        stringBuilder.append(CONTENT_LENGTH)
                .append(body.length).append(CRLF);
    }

    private void appendCookie(StringBuilder stringBuilder) {
        cookies.getCookies().stream().forEach(it -> stringBuilder
                .append(SET_COOKIE)
                .append(it.getName())
                .append(EQUAL_STR)
                .append(it.getValue())
                .append(SEMICOLON_STR).append(CRLF));
    }

    public void addCookie(Cookie cookie) {
        this.cookies.addCookie(cookie);
    }

    public void ok(byte[] body) {
        this.httpStatus = HttpStatus.OK;
        this.body = body;
    }

    public void redirect(String url) {
        this.httpStatus = HttpStatus.FOUND;
        this.responseHeaders.addLocation(url);
    }

    public void internalServerError(Throwable throwable) {
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.body = throwable.getMessage().getBytes();
    }

    public void notFound(Throwable throwable) {
        this.httpStatus = HttpStatus.NOT_FOUND;
        this.body = throwable.getMessage().getBytes();
    }


}
