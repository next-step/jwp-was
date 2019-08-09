package webserver.http;

import utils.StringUtils;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestHeader;
import webserver.http.request.RequestLine;

import java.util.Optional;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static webserver.WebContext.SESSIONS;
import static webserver.WebContext.SESSION_KEY;

public class HttpRequest {
    private String sessionID;
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;
    private HttpParameter mergedHttpParameter;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.mergedHttpParameter = HttpParameter.of(asList(requestLine.getHttpParameter(), requestBody.getHttpParameter()));
    }

    public HttpSession getSession() {
        String id = StringUtils.isNotBlank(sessionID) ? sessionID : requestHeader.getCookie(SESSION_KEY);
        return ofNullable(id)
                .map(SESSIONS::get)
                .orElse(null);
    }

    public void initSession() {
        if (! ofNullable(requestHeader.getCookie(SESSION_KEY))
                .map(SESSIONS::get)
                .isPresent()) {
            createSession();
        }
    }

    private HttpSession createSession() {
        this.sessionID = UUID.randomUUID().toString();
        return SESSIONS.put(sessionID, new HttpSession(sessionID));
    }

    public String getSessionID() {
        return sessionID;
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
