package http.request;

import java.io.BufferedReader;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import http.request.session.HttpSession;
import http.request.session.SessionStore;
import utils.IOUtils;

public class HttpRequest {

    public static final String SESSION_KEY = "SESSION_ID";

    private final RequestLine requestLine;
    private final Headers headers;
    private final RequestBody body;
    private final HttpSession httpSession;

    public HttpRequest(RequestLine requestLine, Headers headers, String body, SessionStore sessionStore) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = new RequestBody(body);
        this.httpSession = sessionStore.fetch(currentClientUserId());
    }

    public static HttpRequest parse(BufferedReader bufferedReader, SessionStore sessionStore) {
        String line = IOUtils.readSingleLine(bufferedReader);
        var requestLine = new RequestLine(line);
        var headers = new Headers(IOUtils.readLines(bufferedReader));

        if (headers.hasBody()) {
            var body = IOUtils.readData(bufferedReader, headers.contentLength());
            return new HttpRequest(requestLine, headers, body, sessionStore);
        }

        return new HttpRequest(requestLine, headers, "", sessionStore);
    }

    public boolean isStaticFile() {
        return requestLine.isStaticFile();
    }

    public String getUrl() {
        return requestLine.getUrl();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public Map<String, String> getParameters() {
        if (getMethod().isGet()) {
            return requestLine.getQueryParams();
        }
        return body.getParameters();
    }

    public Optional<String> getCookie(String key) {
        return headers.getCookie(key);
    }

    public String getFileExtension() {
        return requestLine.getFileExtension();
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public String currentClientUserId() {
        return headers.getCookie(SESSION_KEY)
            .orElse(UUID.randomUUID().toString());
    }
}
