package http;

import org.springframework.util.StringUtils;
import utils.IOUtils;
import utils.StringConstant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static utils.StringConstant.COLON;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeaders headers;
    private final RequestBody body;
    private final Cookies cookies;
    private final HttpSession httpSession;


    private HttpRequest(RequestLine requestLine, RequestHeaders headers, RequestBody body, Cookies cookies, HttpSession httpSession) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
        this.cookies = cookies;
        this.httpSession = httpSession;
    }

    @Nonnull
    public static HttpRequest from(@Nonnull InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line = bufferedReader.readLine();
        if (isEndOfLine(line)) {
            return makeEmptyHttpRequest();
        }

        RequestLine requestLine = RequestLine.from(line);
        RequestHeaders requestHeaders = new RequestHeaders();

        while (true) {
            line = bufferedReader.readLine();

            if (isEndOfLine(line)) {
                break;
            }

            if (isLineBreak(line)) {
                break;
            }

            String[] splitByColon = line.split(COLON);
            if (splitByColon.length < 2) {
                continue;
            }

            requestHeaders.putWithValueTrim(splitByColon[0], splitByColon[1]);
        }

        int contentLengthValue = Integer.parseInt(requestHeaders.getOrDefault("Content-Length", "0"));
        String requestBodyString = IOUtils.readData(bufferedReader, contentLengthValue);
        RequestBody requestBody = RequestBody.from(requestBodyString);
        Cookies cookies = makeCookies(requestHeaders);
        HttpSession httpSession = makeHttpSession(cookies);

        return new HttpRequest(requestLine, requestHeaders, requestBody, cookies, httpSession);
    }

    private static Cookies makeCookies(RequestHeaders requestHeaders) {
        return new Cookies(requestHeaders);
    }

    @Nonnull
    private static HttpSession makeHttpSession(Cookies cookies) {
        String sessionId = cookies.findCookieValue(HttpSessionManager.SESSION_ID_NAME);
        HttpSession httpSession = HttpSessionManager.findSessionById(sessionId);
        if (httpSession == null) {
            return HttpSessionManager.addAndReturnSession();
        }

        return httpSession;
    }

    @Nullable
    public String getCookieValue(@Nonnull String name) {
        return cookies.findCookieValue(name);
    }

    @Nullable
    public Object getQueryValue(@Nonnull String key) {
        Object valueInRequestLine = getRequestLine().getQueryMap().get(key);
        if (valueInRequestLine != null) {
            return valueInRequestLine;
        }

        return getBody().getQueryMap().get(key);
    }

    @Nonnull
    public QueryMap getQueryMap() {
        return QueryMap.mergeQueryMap(getRequestLine().getQueryMap(), getBody().getQueryMap());
    }

    private static boolean isEndOfLine(String line) {
        return line == null;
    }

    private static boolean isLineBreak(String line) {
        return "".equals(line);
    }

    public boolean isGetMethod() {
        return Method.GET.equals(requestLine.getMethod());
    }

    public boolean isPostMethod() {
        return Method.POST.equals(requestLine.getMethod());
    }

    public String getPath() {
        return requestLine.getPath();
    }

    @Nonnull
    public static HttpRequest makeEmptyHttpRequest() {
        return new EmptyHttpRequest();
    }

    static class EmptyHttpRequest extends HttpRequest {
        public EmptyHttpRequest() {
            super(RequestLine.makeEmptyRequestLine(), null, null, null, null);
        }
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeaders getHeaders() {
        return headers;
    }

    public RequestBody getBody() {
        return body;
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }
}
