package webserver.http.request;

import com.google.common.base.Strings;
import utils.IOUtils;
import webserver.http.HttpCookie;
import webserver.http.HttpMethod;
import webserver.http.HttpSession;
import webserver.http.HttpSessionContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static webserver.http.HttpSession.DEFAULT_SESSION_KEY;

public class HttpRequest implements Request {

    private static final String ACCEPT_SEPARATOR = ",";
    private static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final String ACCEPT_KEY = "Accept";
    private static final String COOKIE_KEY = "Cookie";
    private static final String COLON_SEPARATOR = ": ";

    private RequestLine requestLine;
    private Map<String, String> requestHeaders;
    private HttpCookie httpCookies;
    private QueryParam body = QueryParam.EMPTY_QUERY_PARAM;
    private HttpSession session;

    public HttpRequest(InputStream in) throws IOException {
        requestHeaders = new HashMap<>();
        process(in);
        processCookies();
    }

    @Override
    public String getPath() {
        return requestLine.getPath();
    }

    @Override
    public String getHeader(String key) {
        return requestHeaders.get(key);
    }

    @Override
    public String getParameter(String key) {
        return requestLine.getParameter(key);
    }

    @Override
    public HttpMethod getMethod() {
        return requestLine.getHttpMethod();
    }

    @Override
    public void process(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String line = br.readLine();
        requestLine = RequestLine.parse(line);

        while (!Strings.isNullOrEmpty(line = br.readLine())) {
            addHeader(line);
        }

        String contentLength = requestHeaders.get(CONTENT_LENGTH_KEY);
        if (contentLength != null) {
            body = QueryParam.parse(RequestBodyToString(br, contentLength));
        }
    }

    private void processCookies() {

        httpCookies = HttpCookie.parse(requestHeaders.get(COOKIE_KEY));
        session = HttpSessionContext.getSession(httpCookies.get(DEFAULT_SESSION_KEY));
        if(session == null) {
            session = HttpSessionContext.createSession();
        }
        httpCookies.add(DEFAULT_SESSION_KEY, session.getId());
    }

    private void addHeader(String line) {
        String[] keyValue = line.split(COLON_SEPARATOR);
        requestHeaders.put(keyValue[0], keyValue[1]);
    }

    private String RequestBodyToString(BufferedReader br, String contentLength) throws IOException {
        return IOUtils.readData(br, Integer.parseInt(contentLength));
    }

    public boolean isGetRequest() {
        return requestLine.getHttpMethod() == HttpMethod.GET;
    }

    public boolean isPostRequest() {
        return requestLine.getHttpMethod() == HttpMethod.POST;
    }

    public String getBodyParameter(String key) {
        return body.getParameter(key);
    }

    public String getAccept() {
        return requestHeaders.get(ACCEPT_KEY)
                .split(ACCEPT_SEPARATOR)[0];
    }

    public HttpSession getSession() {
        return session;
    }
}
