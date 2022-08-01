package webserver.http.response;

import utils.Assert;
import webserver.http.HttpHeaders;
import webserver.http.SessionAttribute;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

public final class HttpResponse {

    private static final HttpResponse NOT_FOUND = of(HttpStatusCode.NOT_FOUND, ResponseHeader.empty());
    private static final HttpResponse INTERNAL_SERVER_ERROR = of(HttpStatusCode.INTERNAL_SERVER_ERROR, ResponseHeader.empty());

    private final HttpStatusCode code;
    private final ResponseHeader header;
    private final byte[] body;
    private final SessionAttribute sessionAttribute;

    private HttpResponse(HttpStatusCode code, ResponseHeader header, byte[] body, SessionAttribute sessionAttribute) {
        Assert.notNull(code, "'code' must not be null");
        Assert.notNull(header, "'header' must not be null");
        Assert.notNull(body, "'body' must not be null");
        Assert.notNull(sessionAttribute, "'sessionAttribute' must not be null");
        this.code = code;
        this.header = header.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length));
        this.body = body;
        this.sessionAttribute = sessionAttribute;
    }

    public static HttpResponse of(HttpStatusCode code, ResponseHeader header, byte[] body) {
        return new HttpResponse(code, header, body, SessionAttribute.empty());
    }

    public static HttpResponse of(HttpStatusCode code, ResponseHeader header) {
        return of(code, header, new byte[0]);
    }

    public static HttpResponse of(HttpStatusCode code, ResponseHeader header, String body) {
        return of(code, header, body.getBytes(StandardCharsets.UTF_8));
    }

    public static HttpResponse sendRedirect(String path, SessionAttribute sessionAttribute) {
        return new HttpResponse(HttpStatusCode.FOUND, ResponseHeader.from(Map.of(HttpHeaders.LOCATION, path)), new byte[0], sessionAttribute);
    }

    public static HttpResponse sendRedirect(String path) {
        return sendRedirect(path, SessionAttribute.empty());
    }

    public static HttpResponse notFound() {
        return NOT_FOUND;
    }

    public static HttpResponse internalServerError() {
        return INTERNAL_SERVER_ERROR;
    }

    public HttpStatusCode code() {
        return code;
    }

    public int contentLength() {
        return body.length;
    }

    public byte[] body() {
        return body;
    }

    public SessionAttribute sessionAttribute() {
        return sessionAttribute;
    }

    public Set<Map.Entry<String, Object>> headerEntries() {
        return header.entries();
    }

    public boolean emptySessionAttribute() {
        return sessionAttribute.isEmpty();
    }

    public HttpResponse addHeader(String name, Object value) {
        return new HttpResponse(code, header.add(name, value), body, sessionAttribute);
    }
}
