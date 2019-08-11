package webserver.request;

import webserver.request.enums.HttpMethod;
import webserver.request.enums.HttpVersion;

import java.util.Objects;

/**
 * Created by hspark on 2019-08-04.
 */
public class HttpRequest {
    private HttpMethod httpMethod;
    private HttpVersion httpVersion;
    private URI requestUrl;
    private RequestHeaders requestHeaders;
    private RequestParameters requestParameters;
    private Cookies cookies;

    private HttpRequest(RequestLine requestLine,
                        RequestHeaders requestHeaders,
                        RequestParameters requestParameters,
                        Cookies cookies) {
        this.httpMethod = requestLine.getHttpMethod();
        this.httpVersion = requestLine.getHttpVersion();
        this.requestUrl = requestLine.getRequestUrl();
        this.requestHeaders = requestHeaders;
        this.requestParameters = requestParameters;
        this.cookies = cookies;
    }

    public RequestHeaders getRequestHeaders() {
        return requestHeaders;
    }


    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public URI getRequestUrl() {
        return requestUrl;
    }

    public String getPath() {
        return requestUrl.getPath();
    }

    public String getParameter(String name) {
        return requestParameters.getOne(name);
    }

    public String getHeader(String name) {
        return getRequestHeaders().getHeader(name);
    }

    public String getCookie(String name) {
        return cookies.getCookie(name);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public static HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
    }

    public static class HttpRequestBuilder {
        private RequestLine requestLine;
        private RequestHeaders requestHeaders;
        private RequestParameters requestParameters = new RequestParameters();
        private Cookies cookies = new Cookies();


        HttpRequestBuilder() {
            requestHeaders = new RequestHeaders();
        }

        public HttpRequestBuilder requestLine(RequestLine requestLine) {
            this.requestLine = requestLine;
            requestParameters.addParameter(this.requestLine.getRequestUrl().getQueryString());
            return this;
        }

        public HttpRequestBuilder requestHeaders(RequestHeaders headers) {
            this.requestHeaders = headers;
            String rawCookie = headers.getHeader(RequestHeaders.COOKIE);
            if (Objects.nonNull(rawCookie)) {
                this.cookies.addCookieByRawString(rawCookie);
            }
            return this;
        }

        public HttpRequestBuilder requestBody(String body) {
            this.requestParameters.addParameter(body);
            return this;
        }

        public HttpRequest build() {
            Objects.requireNonNull(requestLine);
            return new HttpRequest(requestLine, requestHeaders, requestParameters, cookies);
        }
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "httpMethod=" + httpMethod +
                ", httpVersion=" + httpVersion +
                ", requestUrl=" + requestUrl +
                ", requestHeaders=" + requestHeaders +
                ", requestParameters=" + requestParameters +
                ", cookies=" + cookies +
                '}';
    }
}
