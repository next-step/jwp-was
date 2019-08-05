package webserver.response;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import webserver.request.Cookie;
import webserver.request.Cookies;
import webserver.request.HttpRequest;

/**
 * Created by hspark on 2019-08-05.
 */
public class HttpResponse {
    public static final String CRLF = "\r\n";
    public static final String CONTENT_TYPE = "Content-Type: ";
    public static final String CONTENT_LENGTH = "Content-Length: ";
    public static final String SET_COOKIE = "Set-Cookie: ";
    private HttpStatus httpStatus;
    private ResponseHeaders responseHeaders = new ResponseHeaders();
    private Cookies cookies = new Cookies();

    private byte[] body = new byte[0];

    public HttpResponse() {
    }

    public HttpResponse(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpResponse(HttpStatus httpStatus, byte[] body) {
        this.httpStatus = httpStatus;
        this.body = body;
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

    public String getResponseHeader(HttpRequest httpRequest) {
        StringBuilder stringBuilder = new StringBuilder();
        appendStatusHeader(httpRequest, stringBuilder);
        appendHeaders(stringBuilder);
        if (cookies.hasCookie()) {
            appendCookie(stringBuilder);
        }

        return stringBuilder.append(CRLF).toString();
    }

    private void appendStatusHeader(HttpRequest httpRequest, StringBuilder stringBuilder) {
        stringBuilder.append(httpRequest.getRequestLine().getHttpVersion().getHeaderValue())
                .append(StringUtils.SPACE).append(httpStatus.getStatusCode())
                .append(StringUtils.SPACE).append(httpStatus.name()).append(CRLF)
                .append(CONTENT_TYPE)
                .append(httpRequest.getRequestHeaders().getContentTypeByAccept())
                .append(CRLF).append(CONTENT_LENGTH)
                .append(body.length).append(CRLF);
    }

    private void appendHeaders(StringBuilder stringBuilder) {
        responseHeaders.getHeaders()
                .entrySet()
                .forEach(it -> stringBuilder.append(it.getKey() + ": " + it.getValue()).append(CRLF));
    }

    private void appendCookie(StringBuilder stringBuilder) {
        cookies.getCookies().stream().forEach(it -> stringBuilder
                .append(SET_COOKIE)
                .append(it.getName())
                .append("=")
                .append(it.getValue())
                .append(";").append(CRLF));
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
