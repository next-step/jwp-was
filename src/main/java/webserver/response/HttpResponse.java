package webserver.response;

import utils.Assert;
import webserver.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class HttpResponse {

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

    public static class Builder {

        private final HttpStatusCode code;
        private final Map<String, String> header = new HashMap<>();
        private byte[] body = new byte[0];

        private Builder(HttpStatusCode code) {
            this.code = code;
        }

        public static Builder status(HttpStatusCode status) {
            return new Builder(status);
        }

        public static Builder ok() {
            return status(HttpStatusCode.OK);
        }

        public static Builder ok(String body) {
            return status(HttpStatusCode.OK)
                    .body(body);
        }

        public static Builder ok(byte[] body) {
            return status(HttpStatusCode.OK)
                    .body(body);
        }

        public static Builder sendRedirect(String path) {
            return status(HttpStatusCode.FOUND)
                    .addHeader(HttpHeaders.LOCATION, path);
        }

        public static Builder notFound() {
            return status(HttpStatusCode.NOT_FOUND);
        }

        public static Builder internalServerError() {
            return status(HttpStatusCode.INTERNAL_SERVER_ERROR);
        }

        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        public Builder body(String body) {
            this.body = body.getBytes(StandardCharsets.UTF_8);
            return this;
        }

        public Builder addHeaders(Map<String, String> header) {
            this.header.putAll(header);
            return this;
        }

        public Builder addHeader(String key, String value) {
            this.header.put(key, value);
            return this;
        }

        public Builder contentType(String value) {
            return addHeader(HttpHeaders.CONTENT_TYPE, value);
        }

        public HttpResponse build() {
            header.put(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length));
            return new HttpResponse(code, ResponseHeader.from(header), body);
        }
    }
}
