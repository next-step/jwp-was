package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import utils.IOUtils;
import webserver.session.HttpSession;
import webserver.session.HttpSessionContext;
import webserver.session.HttpSessionIdHolder;
import webserver.session.UUIDSessionIdGenerator;

public class HttpRequestParser {

    private static final String SESSION_COOKIE_NAME = "JWP_SESSION_ID";

    private HttpRequestParser() {
        throw new AssertionError();
    }

    public static HttpRequest parse(final BufferedReader bufferedReader) throws IOException {
        final RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
        final RequestHeaders requestHeaders = parseHeaders(bufferedReader);
        final RequestBody requestBody = parseBody(bufferedReader, requestHeaders);

        createSession(requestHeaders.getCookie(SESSION_COOKIE_NAME));

        return new HttpRequest(requestLine, requestHeaders, requestBody);
    }

    private static void createSession(final String sessionId) {
        HttpSessionIdHolder.generate(getCookieSessionIdOrNewSessionId(sessionId));
    }

    private static String getCookieSessionIdOrNewSessionId(final String sessionId) {
        if (!HttpSessionContext.has(sessionId)) {
            final HttpSession httpSession = new HttpSession(new UUIDSessionIdGenerator());
            HttpSessionContext.add(httpSession);
            return httpSession.getId();
        }
        return sessionId;
    }

    private static RequestHeaders parseHeaders(final BufferedReader bufferedReader) throws IOException {
        final RequestHeaders requestHeaders = new RequestHeaders();
        String header = bufferedReader.readLine();
        while (!header.isEmpty()) {
            requestHeaders.add(header);
            header = bufferedReader.readLine();
        }
        return requestHeaders;
    }

    private static RequestBody parseBody(final BufferedReader bufferedReader, final RequestHeaders requestHeaders) throws IOException {
        if (requestHeaders.hasRequestBody()) {
            final String body = IOUtils.readData(bufferedReader, requestHeaders.getContentLength());
            return new RequestBody(body);
        }
        return RequestBody.EMPTY_REQUEST_BODY;
    }
}
