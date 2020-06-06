package http.responses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseContext {

    private static final String DEFAULT_HTTP_VERSION = "1.1";

    private final String version;
    private final HttpStatus status;
    private final Map<String, String> responseHeaders;
    private final byte[] body;

    private ResponseContext(String version, HttpStatus status, Map<String, String> responseHeaders, byte[] body) {
        this.version = version;
        this.status = status;
        this.responseHeaders = responseHeaders;
        this.body = body;
    }

    public String getStatusLine() {
        return String.format("HTTP/%s %d %s \r\n", version, status.getStatusCode(), status.getReasonPhrase());
    }

    public List<String> getResponseHeaderList() {
        return responseHeaders.entrySet().stream()
                .map(e -> String.format("%s: %s\r\n", e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public byte[] getResponseBody() {
        return body;
    }

    public static ResponseContextBuilder builder() {
        return new ResponseContextBuilder();
    }

    public static class ResponseContextBuilder {

        private String version = DEFAULT_HTTP_VERSION;
        private HttpStatus status;
        private final Map<String, String> responseHeaders = new HashMap<>();
        private byte[] body;

        private ResponseContextBuilder() {
        }

        public ResponseContextBuilder version(String version) {
            this.version = version;
            return this;
        }

        public ResponseContextBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ResponseContextBuilder addHeader(String key, String value) {
            this.responseHeaders.put(key, value);
            return this;
        }

        public ResponseContextBuilder body(byte[] body) {
            this.body = body;
            return this;
        }

        public ResponseContext build() {
            final String version = this.version != null ? this.version : DEFAULT_HTTP_VERSION;
            return new ResponseContext(version, status, responseHeaders, body);
        }
    }
}
