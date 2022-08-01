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

    public static HttpResponse ok(HttpStatusCode httpStatusCode, ResponseHeader responseHeader, byte[] bytes) {
        return new HttpResponse(httpStatusCode, responseHeader, bytes);
    }

    public static HttpResponse notFound() {
        return new HttpResponse(HttpStatusCode.NOT_FOUND, ResponseHeader.empty(), new byte[0]);
    }

    public static HttpResponse of(HttpStatusCode httpStatusCode, ResponseHeader responseHeader, byte[] body) {
        return new HttpResponse(httpStatusCode, responseHeader, body);
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
}
