package model;

import java.nio.charset.StandardCharsets;
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

    public static HttpResponse notFound() {
        return new HttpResponse(HttpStatusCode.NOT_FOUND, ResponseHeader.empty(), new byte[0]);
    }

    public static HttpResponse of(HttpStatusCode httpStatusCode, ResponseHeader responseHeader, byte[] body) {
        return new HttpResponse(httpStatusCode, responseHeader, body);
    }

    public static HttpResponse of(HttpStatusCode httpStatusCode, ResponseHeader responseHeader, String body) {
        return new HttpResponse(httpStatusCode, responseHeader, body.getBytes(StandardCharsets.UTF_8));
    }

    public static HttpResponse of(HttpStatusCode httpStatusCode, ResponseHeader responseHeader) {
        return new HttpResponse(httpStatusCode, responseHeader, new byte[0]);
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
