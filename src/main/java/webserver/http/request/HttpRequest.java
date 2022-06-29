package webserver.http.request;

import utils.IOUtils;
import webserver.http.Cookie;
import webserver.http.HttpMethod;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSession;
import webserver.http.session.HttpSessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.util.Objects.requireNonNull;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeaders headers;
    private final RequestBody body;

    // The response with which this request is associated.
    private HttpResponse response;

    public HttpRequest(final InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        this.requestLine = convertToRequestLine(br);
        this.headers = convertToRequestHeaders(br);
        this.body = convertToRequestBody(br);
    }

    private RequestLine convertToRequestLine(final BufferedReader br) throws IOException {
        return RequestLine.parse(br.readLine());
    }

    private RequestHeaders convertToRequestHeaders(final BufferedReader br) throws IOException {
        final RequestHeaders.Builder builder = RequestHeaders.builder();

        String headerNameAndValue = br.readLine();
        while (!"".equals(headerNameAndValue)) {
            builder.add(headerNameAndValue);
            headerNameAndValue = br.readLine();
        }

        return builder.build();
    }

    private RequestBody convertToRequestBody(final BufferedReader br) throws IOException {
        if (this.headers.hasRequestBody()) {
            final String requestBody = IOUtils.readData(br, this.headers.getContentLength());
            return new RequestBody(requestBody);
        }

        return null;
    }

    public RequestLine getRequestLine() {
        return this.requestLine;
    }

    public RequestHeaders getRequestHeaders() {
        return this.headers;
    }

    public RequestBody getBody() {
        return this.body;
    }

    public HttpMethod getMethod() {
        return this.requestLine.getMethod();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public Cookie getCookieOrNull(final String cookieName) {
        return this.headers.getCookieOrNull(cookieName);
    }

    public void setResponse(final HttpResponse response) {
        this.response = response;
    }

    public HttpSession getSession() {
        requireNonNull(this.response);

        final HttpSession existingSession = this.getExistingSessionOrNull();
        if (existingSession != null) {
            return existingSession;
        }

        final HttpSession session = HttpSessionManager.createSession();
        this.response.setCookie(new Cookie(HttpSessionManager.SESSION_COOKIE_NAME, session.getId()));
        return session;
    }

    private HttpSession getExistingSessionOrNull() {
        final Cookie sessionCookie = this.getCookieOrNull(HttpSessionManager.SESSION_COOKIE_NAME);

        if (sessionCookie == null) {
            return null;
        }

        return HttpSessionManager.getSessionOrNull(sessionCookie.getValue());
    }
}
