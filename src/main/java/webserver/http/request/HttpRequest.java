package webserver.http.request;

import utils.Assert;
import webserver.http.HttpSession;
import webserver.http.Session;
import webserver.http.SessionConfig;
import webserver.http.SessionStorage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class HttpRequest {

    private static final int REQUEST_LINE_INDEX = 0;

    private final SessionStorage sessionStorage;
    private final RequestLine requestLine;
    private final RequestHeader header;
    private final RequestBody body;

    private Session sessionCache;

    private HttpRequest(SessionStorage sessionStorage, RequestLine requestLine, RequestHeader header, RequestBody body) {
        Assert.notNull(sessionStorage, "'sessionStorage' must not be null");
        Assert.notNull(requestLine, "'requestLine' must not be null");
        Assert.notNull(header, "'header' must not be null");
        Assert.notNull(body, "'body' must not be null");
        this.sessionStorage = sessionStorage;
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public static HttpRequest of(SessionStorage sessionStorage, List<String> request) {
        validate(request);
        return new HttpRequest(
                sessionStorage,
                RequestLine.from(request.get(REQUEST_LINE_INDEX)),
                RequestHeader.from(request.subList(REQUEST_LINE_INDEX, request.size())),
                RequestBody.empty());
    }

    public static HttpRequest of(HttpRequest request, RequestBody body) {
        return new HttpRequest(request.sessionStorage, request.requestLine, request.header, body);
    }

    private static void validate(List<String> request) {
        if (request == null || request.isEmpty()) {
            throw new IllegalArgumentException("request must not be empty");
        }
    }

    public Path path() {
        return requestLine.path();
    }

    public Optional<String> queryValue(String parameter) {
        return requestLine.queryValue(parameter);
    }

    public Optional<String> bodyValue(String key) {
        return body.value(key);
    }

    public boolean matchMethod(HttpMethod method) {
        return requestLine.matchMethod(method);
    }

    public Optional<String> cookieValue(String name) {
        return header.cookieValue(name);
    }

    public String sessionId() {
        return session().getId();
    }

    public void setSessionAttribute(String name, Object value) {
        session().setAttribute(name, value);
    }

    public boolean containsSessionAttribute(String name) {
        return session().containsAttribute(name);
    }

    public int contentLength() {
        return header.contentLength();
    }

    public boolean matchMethodAndPath(HttpMethod method, Path path) {
        return matchMethod(method) && requestLine.matchPath(path);
    }

    public HttpRequest withBody(RequestBody body) {
        return of(this, body);
    }

    private Session session() {
        if (sessionCache != null) {
            return sessionCache;
        }
        sessionCache = cookieValue(SessionConfig.sessionCookieName())
                .flatMap(sessionStorage::find)
                .orElseGet(this::newSession);
        return sessionCache;
    }

    private Session newSession() {
        Session session = HttpSession.empty();
        sessionStorage.add(session);
        return session;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, header, body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpRequest request = (HttpRequest) o;
        return Objects.equals(requestLine, request.requestLine) && Objects.equals(header, request.header) && Objects.equals(body, request.body);
    }
}
