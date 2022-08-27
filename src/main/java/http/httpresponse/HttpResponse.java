package http.httpresponse;

import http.SessionAttribute;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class HttpResponse {
    private static final HttpResponse NOT_FOUND = new HttpResponse(new StatusLine(HttpStatusCode.NOT_FOUND), ResponseHeader.empty());
    private static final HttpResponse INTERNAL_SERVER_ERROR = new HttpResponse(new StatusLine(HttpStatusCode.INTERNAL_SERVER_ERROR), ResponseHeader.empty());

    private final StatusLine statusLine;
    private final ResponseHeader responseHeader;
    private final byte[] body;
    private final SessionAttribute sessionAttribute;

    public HttpResponse(StatusLine statusLine,
                        ResponseHeader responseHeader,
                        byte[] body,
                        SessionAttribute sessionAttribute) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.body = body;
        this.sessionAttribute = sessionAttribute;
    }

    public HttpResponse(StatusLine statusLine, ResponseHeader responseHeader) {
        this(statusLine, responseHeader, new byte[0], SessionAttribute.empty());
    }

    public HttpResponse(StatusLine statusLine,
                        ResponseHeader responseHeader,
                        String body) {
        this(statusLine, responseHeader, body.getBytes(StandardCharsets.UTF_8), SessionAttribute.empty());
    }

    public HttpResponse(StatusLine statusLine,
                        ResponseHeader responseHeader,
                        byte[] body) {
        this(statusLine, responseHeader, body, SessionAttribute.empty());
    }

    public static HttpResponse sendRedirect(String path) {
        return  sendRedirect(path, new ResponseHeader(Collections.emptyMap()));
    }

    public static HttpResponse sendRedirect(String path, ResponseHeader responseHeader) {
        return new HttpResponse(new StatusLine(HttpStatusCode.FOUND), responseHeader.add(HttpHeaders.LOCATION, path));
    }

    public static HttpResponse sendRedirect(String path, SessionAttribute sessionAttribute) {
        return new HttpResponse(new StatusLine(HttpStatusCode.FOUND), new ResponseHeader(Map.of(HttpHeaders.LOCATION, path)), new byte[0], sessionAttribute);
    }

    public HttpStatusCode getHttpStatusCode() {
        return statusLine.getHttpStatusCode();
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public byte[] getBody() {
        return body;
    }

    public int getContentLength() {
        return body.length;
    }

    public static HttpResponse notFound() {
        return NOT_FOUND;
    }

    public static HttpResponse internalServerError() {
        return INTERNAL_SERVER_ERROR;
    }

    public SessionAttribute getSessionAttribute() {
        return sessionAttribute;
    }

    public boolean isEmptySessionAttribute() {
        return sessionAttribute.isEmpty();
    }

    public HttpResponse addHeader(String name, Object value) {
        return new HttpResponse(statusLine, responseHeader.add(name, value), body, sessionAttribute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponse that = (HttpResponse) o;
        return statusLine.equals(that.statusLine) && responseHeader.equals(that.responseHeader) && Arrays.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(statusLine, responseHeader);
        result = 31 * result + Arrays.hashCode(body);
        return result;
    }
}
