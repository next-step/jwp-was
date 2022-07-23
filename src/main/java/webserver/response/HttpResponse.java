package webserver.response;

import utils.Assert;
import webserver.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

public final class HttpResponse {

    public static final HttpResponse NOT_FOUND = of(HttpStatusCode.NOT_FOUND, ResponseHeader.empty());

    private final HttpStatusCode code;
    private final ResponseHeader header;
    private final byte[] body;

    private HttpResponse(HttpStatusCode code, ResponseHeader header, byte[] body) {
        Assert.notNull(code, "'code' must not be null");
        Assert.notNull(header, "'header' must not be null");
        Assert.notNull(body, "'body' must not be null");
        this.code = code;
        this.header = header.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length));
        this.body = body;
    }

    public static HttpResponse of(HttpStatusCode code, ResponseHeader header) {
        return new HttpResponse(code, header, new byte[0]);
    }

    public static HttpResponse of(HttpStatusCode code, ResponseHeader header, byte[] body) {
        return new HttpResponse(code, header, body);
    }

    public static HttpResponse of(HttpStatusCode code, ResponseHeader header, String body) {
        return new HttpResponse(code, header, body.getBytes(StandardCharsets.UTF_8));
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

    public Set<Map.Entry<String, String>> headerEntries() {
        return header.entries();
    }
}
