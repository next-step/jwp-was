package http.request;

import http.common.Cookies;
import http.common.HeaderFieldName;
import http.response.HttpResponse;
import webserver.exceptions.FailedSessionCreateException;
import webserver.session.HttpSession;

import java.util.Optional;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader header;
    private final Parameters formData;
    private final Cookies cookies;
    private final String body;
    private HttpResponse httpResponse;
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

    public Optional<String> getHeader(String headerName) {
        return header.getValue(headerName);
    }

    public Optional<String> getHeader(HeaderFieldName headerName) {
        return header.getValue(headerName);
    }

    public HttpSession getSession() {
        if (session == null) {
            this.session = getSession(true)
                .orElseThrow(FailedSessionCreateException::new);
        }
        return session;
    }

    public Optional<HttpSession> getSession(boolean create) {
        if (session == null && create) {
            this.session = createSession();
        }
        return Optional.ofNullable(session);
    }

    private HttpSession createSession() {
        HttpSession session = HttpSession.create();
        httpResponse.setSessionId(session.getId());
        return session;
    }

    public String getParameter(String parameterName) {
        String parameterValue = requestLine.getParameter(parameterName);
        if ("".equals(parameterValue)) {
            parameterValue = formData.getValue(parameterName);
        }
        return parameterValue;
    }

    public void setHttpResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }
}
