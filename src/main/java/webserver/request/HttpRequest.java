package webserver.request;

import java.util.Objects;

/**
 * Created by hspark on 2019-08-04.
 */
public class HttpRequest {
    private RequestLine requestLine;
    private Headers headers;

    private HttpRequest(RequestLine requestLine, Headers headers) {
        this.requestLine = requestLine;
        this.headers = headers;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Headers getHeaders() {
        return headers;
    }

    public String getPath() {
        return requestLine.getRequestUrl().getPath();
    }

    public static HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
    }

    public static class HttpRequestBuilder {
        private RequestLine requestLine;
        private Headers headers;


        HttpRequestBuilder() {
            headers = new Headers();
        }

        public HttpRequestBuilder requestLine(String rawRequestLine) {
            this.requestLine = RequestLine.parse(rawRequestLine);
            return this;
        }

        public HttpRequestBuilder addHeader(String rawHeader) {
            this.headers.add(rawHeader);
            return this;
        }

        public HttpRequest build() {
            Objects.requireNonNull(requestLine);
            return new HttpRequest(requestLine, headers);
        }
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                '}';
    }
}
