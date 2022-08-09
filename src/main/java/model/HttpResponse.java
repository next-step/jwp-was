package model;

import java.util.Map;
import java.util.Set;

public class HttpResponse {
    private final HttpStatusCode code;
    private final ResponseHeader header;
    private final byte[] body;

    private HttpResponse(HttpStatusCode httpStatusCode, ResponseHeader responseHeader, byte[] body) {
        this.code = httpStatusCode;
        this.header = responseHeader;
        this.body = body;
    }

    public static HttpResponse found(String path) {
        return new HttpResponse(
                HttpStatusCode.FOUND,
                ResponseHeader.of(Map.of(HttpHeaders.LOCATION, path)),
                new byte[0]
        );
    }

    public static HttpResponse notFound() {
        return new HttpResponse(HttpStatusCode.NOT_FOUND, ResponseHeader.empty(), new byte[0]);
    }

    public static HttpResponse ok(ResponseHeader responseHeader, byte[] body) {
        return new HttpResponse(HttpStatusCode.OK, responseHeader, body);
    }

    public static HttpResponse found(String path, String cookie) {
        return new HttpResponse(
                HttpStatusCode.FOUND,
                ResponseHeader.of(Map.of(HttpHeaders.LOCATION, path), cookie),
                new byte[0]);
    }

    public static HttpResponse error() {
        return new HttpResponse(HttpStatusCode.INTERNAL_SERVER_ERROR, ResponseHeader.empty(), new byte[0]);
    }

    public String getHttpResponseCode() {
        return code.getHttpResponseCode();
    }

    public byte[] getBody() {
        return body;
    }

    public Set<Map.Entry<String, Object>> getHeaders() {
        return header.getHeaders().entrySet();
    }

    public boolean containsCookie() {
        return header.containsCookie();
    }

    public String getCookie() {
        return header.getCookie();
    }
}
