package http.request;

import http.common.Cookies;
import http.common.HeaderFieldName;
import webserver.session.HttpSession;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader header;
    private final Parameters formData;
    private final Cookies cookies;
    private final String body;
    private HttpSession session;

    public HttpRequest(RequestLine requestLine, RequestHeader header, Parameters formData, Cookies cookies, String body, HttpSession session) {
        this.requestLine = requestLine;
        this.header = header;
        this.formData = formData;
        this.cookies = cookies;
        this.body = body;
        this.session = session;
    }

    public HttpMethod getMethod() {
        return requestLine.getHttpMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getBody() {
        return body;
    }

    public String getCookie(String cookieName) {
        return cookies.getValue(cookieName);
    }

    public String getHeader(String headerName) {
        return header.getValue(headerName);
    }

    public String getHeader(HeaderFieldName headerName) {
        return header.getValue(headerName);
    }

    public HttpSession getSession(boolean create) {
        if (session == null && create) {
            this.session = HttpSession.create();
        }
        return session;
    }

    public String getParameter(String parameterName) {
        String parameterValue = requestLine.getParameter(parameterName);
        if ("".equals(parameterValue)) {
            parameterValue = formData.getValue(parameterName);
        }
        return parameterValue;
    }
}
