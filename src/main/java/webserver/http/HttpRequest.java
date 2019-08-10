package webserver.http;

import webserver.http.request.RequestBody;
import webserver.http.request.RequestHeader;
import webserver.http.request.RequestLine;

import java.util.Optional;

import static java.util.Arrays.asList;
import static webserver.WebContext.SESSION_KEY;

public class HttpRequest {
    private String sessionID;
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;
    private HttpSession httpSession;
    private HttpParameter mergedHttpParameter;

    public HttpRequest(RequestLine requestLine,
                       RequestHeader requestHeader,
                       RequestBody requestBody,
                       HttpSessionManager sessionManager) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.httpSession = sessionManager.getSession(requestHeader.getCookie(SESSION_KEY));
        this.mergedHttpParameter = HttpParameter.of(asList(requestLine.getHttpParameter(), requestBody.getHttpParameter()));
    }

    public String getSessionId() {
        return httpSession.getId();
    }

    public HttpSession getSession() {
        return httpSession;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public HttpParameter getMergedHttpParameter() {
        return mergedHttpParameter;
    }

    public String getResponseContentType() {
        return Optional.of(requestHeader)
                .map(RequestHeader::getAccept)
                .map(accepts -> accepts.split(",")[0])
                .orElse("text/html");
    }

}
