package webserver.http.request;

/**
 * @author : yusik
 * @date : 2019-08-10
 */
public class TestRequest {

    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String HEADER_SEPARATOR = ": ";
    private static final String QUERY_SEPARATOR = "&";
    private static final String QUERY_VALUE_DELIMITER = "=";
    private static final String SP = " ";
    private static final String NEW_LINE = "\r\n";

    private final String method;
    private final String path;
    private String headers;
    private String body;

    public static class Builder {
        private final String method;
        private final String path;
        private StringBuilder headers = new StringBuilder();
        private StringBuilder body = new StringBuilder();

        public Builder(String method, String path) {
            this.method = method;
            this.path = path;
        }

        public Builder addHeader(String field, String value) {
            headers.append(field).append(HEADER_SEPARATOR).append(value).append(NEW_LINE);
            return this;
        }

        public Builder addBody(String key, String value) {
            if (body.length() != 0) {
                body.append(QUERY_SEPARATOR);
            }
            body.append(key).append(QUERY_VALUE_DELIMITER).append(value);
            return this;
        }

        public TestRequest build() {
            return new TestRequest(this);
        }

        public String buildString() {
            TestRequest request = new TestRequest(this);
            return request.toString();
        }
    }

    public TestRequest(Builder builder) {
        this.method = builder.method;
        this.path = builder.path;
        this.headers = builder.headers.toString();
        this.body = builder.body.toString();
    }

    @Override
    public String toString() {
        return method + SP + path + SP + HTTP_VERSION + NEW_LINE +
                headers +
                NEW_LINE +
                body;
    }
}
