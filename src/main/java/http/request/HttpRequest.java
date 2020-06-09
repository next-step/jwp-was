package http.request;

import http.HttpSession;
import http.HttpSessions;
import lombok.Getter;
import utils.ConvertUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Getter
public class HttpRequest {
    private static final String CHAR_SET = "UTF-8";
    private static final String SESSION_ID = "JSESSONID";

    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;
    private HttpSession session;

    private HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody, HttpSession session) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.session = session;
    }

    private HttpRequest(RequestLine requestLine, RequestHeader requestHeader, HttpSession session) {
        this(requestLine, requestHeader, null, session);
    }

    public static HttpRequest parse(InputStream inputStream, HttpSessions httpSessions) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())));

        RequestLine requestLine = RequestLine.parse(br.readLine());
        RequestHeader requestHeader = RequestHeader.parse(br);

        HttpSession session = findSession(requestHeader, httpSessions);

        if (requestLine.getMethod() == HttpMethod.POST) {
            String body = IOUtils.readData(br, requestHeader.getContentLength());

            RequestBody requestBody = RequestBody.parse(URLDecoder.decode(body, CHAR_SET));
            return new HttpRequest(requestLine, requestHeader, requestBody, session);
        }

        return new HttpRequest(requestLine, requestHeader, session);
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public String getFilePath() {
        return this.requestLine.getFilePath();
    }

    public String getContentType() {
        return this.requestHeader.getContentType();
    }

    public <T> T getBody(Class<T> valueType) {
        return ConvertUtils.convertValue(this.requestBody.getBodyMap(), valueType);
    }

    public HttpMethod getMethod() {
        return this.requestLine.getMethod();
    }

    public boolean loggedIn() {
        return this.requestHeader.loggedIn();
    }

    public String getParameter(String key) {
        return this.requestLine.getParameter(key);
    }

    public HttpSession getSession() {
        if (sessionIsNull()) {
            this.session = new HttpSession(UUID.randomUUID().toString());
        }
        return this.session;
    }

    public boolean sessionIsNull() {
        return Objects.isNull(session);
    }

    private static HttpSession findSession(RequestHeader requestHeader, HttpSessions httpSessions) {
        RequestCookie cookie = requestHeader.getRequestCookie();
        if(Objects.isNull(cookie)) {
            return null;
        }

        HttpSession session = null;
        String sessionId = cookie.get(SESSION_ID);

        if (Objects.nonNull(sessionId) && httpSessions.containsKey(sessionId)) {
            session = httpSessions.getSession(sessionId);
        }

        return session;
    }
}
