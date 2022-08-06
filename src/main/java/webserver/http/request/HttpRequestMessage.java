package webserver.http.request;

import webserver.http.header.HttpCookie;
import webserver.http.request.body.HttpRequestBody;
import webserver.http.request.header.HttpRequestHeaders;
import webserver.http.request.requestline.HttpMethod;
import webserver.http.request.requestline.HttpQueryStrings;
import webserver.http.request.requestline.HttpRequestLine;
import webserver.http.session.HttpSession;

import static webserver.http.session.HttpSessionStore.SESSION_ID_KEY;

public class HttpRequestMessage {
    private final HttpRequestLine httpRequestLine;
    private final HttpRequestHeaders httpRequestHeaders;
    private HttpRequestBody httpRequestBody;

    public HttpRequestMessage(HttpRequestLine httpRequestLine, HttpRequestHeaders httpRequestHeaders) {
        this.httpRequestLine = httpRequestLine;
        this.httpRequestHeaders = httpRequestHeaders;
    }

    public HttpRequestMessage(HttpRequestLine httpRequestLine, HttpRequestHeaders httpRequestHeaders, HttpRequestBody httpRequestBody) {
        this.httpRequestLine = httpRequestLine;
        this.httpRequestHeaders = httpRequestHeaders;
        this.httpRequestBody = httpRequestBody;
    }

    public HttpMethod httpMethod() {
        return httpRequestLine.getHttpMethod();
    }

    public String httpPath() {
        return httpRequestLine.getHttpPath().getFullPath();
    }

    public String getHeader(String headerName) {
        return httpRequestHeaders.get(headerName);
    }

    public HttpCookie getCookie(String cookieKey) {
        return httpRequestHeaders.getCookie(cookieKey);
    }

    public String getBodyValue(String bodyName) {
        return httpRequestBody.getBodyValue(bodyName);
    }

    public HttpQueryStrings getHttpQueryStrings() {
        assert httpRequestLine.getHttpPath() != null;

        return httpRequestLine.getHttpPath().getHttpQueryStrings();
    }

    public HttpSession getSession() {
        return httpRequestHeaders.getSession();
    }

    public boolean isNotExistSession() {
        return getCookie(SESSION_ID_KEY).isNone();
    }
}
