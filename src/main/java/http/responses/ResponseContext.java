package http.responses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @see <a href=https://tools.ietf.org/html/rfc2616#section-6>Response Specification</a>
 * 요약:
 * HTTP버전 상태코드 설명구문
 * 응답헤더(요청 헤더처럼)
 */
public class ResponseContext {

    private static final String DEFAULT_HTTP_VERSION = "1.1";

    private final String httpVersion;
    private final HttpStatus status;
    private final Map<String, String> responseHeaders;
    private final byte[] body;

    // TODO: nullable 어떻게 표현할지 고민..
    private ResponseContext(String httpVersion, HttpStatus status, Map<String, String> responseHeaders, byte[] body) {
        this.httpVersion = httpVersion;
        this.status = status;
        this.responseHeaders = responseHeaders;
        this.body = body;
    }

    // TODO: 이 컨텍스트를 바탕으로 응답 렌더링 하는 부분은 외부로 빼자
    public String getStatusLine() {
        return String.format("HTTP/%s %d %s \r\n", httpVersion, status.getStatusCode(), status.getReasonPhrase());
    }

    // TODO: 이 컨텍스트를 바탕으로 응답 렌더링 하는 부분은 외부로 빼자
    public List<String> getResponseHeaderList() {
        return responseHeaders.entrySet().stream()
                .map(e -> String.format("%s: %s\r\n", e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    // TODO: 이 컨텍스트를 바탕으로 응답 렌더링 하는 부분은 외부로 빼자
    public byte[] getResponseBody() {
        return body;
    }

    @Override
    public String toString() {
        return "ResponseContext{" +
                "httpVersion='" + httpVersion + '\'' +
                ", status=" + status +
                ", responseHeaders=" + responseHeaders +
                '}';
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
