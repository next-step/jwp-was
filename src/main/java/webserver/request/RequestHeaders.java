package webserver.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class RequestHeaders {
    private final Map<String, String> headers;

    public static Builder builder() {
        return new Builder();
    }

    private RequestHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }

    public String get(final String headerName) {
        final String valueOrNull = this.getOrNull(headerName);

        if (valueOrNull == null) {
            throw new IllegalArgumentException("Header not found: " + headerName);
        }

        return valueOrNull;
    }

    public String getOrNull(final String headerName) {
        return this.headers.get(headerName);
    }

    public boolean hasRequestBody() {
        return this.headers.containsKey("Content-Length");
    }

    public String getHost() {
        return this.get("Host");
    }

    public String getConnection() {
        return this.get("Connection");
    }

    public int getContentLength() {
        return Integer.parseInt(this.get("Content-Length"));
    }

    public String getContentType() {
        return this.get("Content-Type");
    }

    public String getAccept() {
        return this.get("Accept");
    }

    public static class Builder {
        private final List<String> headerNameAndValues = new ArrayList<>();

        public void add(final String headerNameAndValue) {
            this.headerNameAndValues.add(headerNameAndValue);
        }

        public RequestHeaders build() {
            final Map<String, String> headers = this.headerNameAndValues.stream()
                    .map(it -> it.split(": "))
                    .collect(toMap(
                            it -> it[0],
                            it -> it[1]
                    ));
            return new RequestHeaders(headers);
        }
    }
}
