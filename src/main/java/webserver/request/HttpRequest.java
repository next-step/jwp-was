package webserver.request;

import java.util.Objects;

/**
 * Created by hspark on 2019-08-04.
 */
public class HttpRequest {
    private RequestLine requestLine;
    private RequestHeaders requestHeaders;
    private RequestBody requestBody;

    private HttpRequest(RequestLine requestLine, RequestHeaders requestHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public String getPath() {
        return requestLine.getRequestUrl().getPath();
    }

    public String getAttribute(String name) {
        return requestLine.getRequestUrl().get(name);
    }

    public String getHeader(String name) {
        return getRequestHeaders().getHeader(name);
    }

    public static HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
    }

    public static class HttpRequestBuilder {
        private RequestLine requestLine;
        private RequestHeaders requestHeaders;
        private RequestBody requestBody;


        HttpRequestBuilder() {
            requestHeaders = new RequestHeaders();
        }

        public HttpRequestBuilder requestLine(RequestLine requestLine) {
            this.requestLine = requestLine;
            return this;
        }

        public HttpRequestBuilder requestHeaders(RequestHeaders headers) {
            this.requestHeaders = headers;
            return this;
        }

        public HttpRequestBuilder requestBody(RequestBody requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public HttpRequest build() {
            Objects.requireNonNull(requestLine);
            return new HttpRequest(requestLine, requestHeaders, requestBody);
        }
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", requestHeaders=" + requestHeaders +
                '}';
    }
}
