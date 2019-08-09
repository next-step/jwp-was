package webserver.request;

import utils.IOUtils;
import webserver.HttpHeaders;
import webserver.HttpSession;
import webserver.Request;
import webserver.response.HeaderProperty;
import webserver.session.HttpUUIDSession;
import webserver.session.SessionContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

import static webserver.response.HeaderProperty.ACCEPT;
import static webserver.response.HeaderProperty.COOKIE;

public class HttpRequest implements Request {

    private static final String UTF_8 = "UTF-8";
    private static final int DEFAULT_OF_CONTENT_LENGTH = -1;

    private RequestLine requestLine;
    private HttpHeaders httpHeaders;
    private RequestBody requestBody;

    private HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.requestBody = requestBody;
    }

    public static Request newInstance(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, UTF_8));
        RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
        HttpHeaders httpHeaders = generateHeaders(bufferedReader);

        if (httpHeaders.getContentLength() > DEFAULT_OF_CONTENT_LENGTH) {
            String requestBody = IOUtils.readData(bufferedReader, httpHeaders.getContentLength());
            return new HttpRequest(requestLine, httpHeaders, RequestBody.parse(requestBody));
        }
        return new HttpRequest(requestLine, httpHeaders, RequestBody.EMPTY);
    }

    private static HttpHeaders generateHeaders(BufferedReader bufferedReader) throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        String headerInfo;
        while ((headerInfo = bufferedReader.readLine()) != null) {
            if (headerInfo.isEmpty()) {
                break;
            }
            httpHeaders.add(headerInfo);
        }
        return httpHeaders;
    }

    @Override
    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    @Override
    public String getParameter(String field) {
        return Optional.ofNullable(requestLine.getRequestParam(field))
                .orElse(requestBody.get(field));
    }

    @Override
    public String getPath() {
        return requestLine.getPath();
    }

    @Override
    public Cookies getCookies() {
        return Cookies.of(httpHeaders.get(COOKIE.getHeaderName()));
    }

    @Override
    public String getAccept() {
        return httpHeaders.get(ACCEPT.getHeaderName()).split(",")[0];
    }

    @Override
    public boolean matchPath(String path) {
        return requestLine.matchPath(path);
    }

    @Override
    public boolean containPath(String path) {
        return requestLine.containPath(path);
    }

    @Override
    public String getHeader(HeaderProperty headerProperty) {
        return httpHeaders.get(headerProperty);
    }

    @Override
    public HttpSession getSession() {
        String jsessionid = getCookie("JSESSIONID");

        if(jsessionid == null){
            return initSession();
        }
        return SessionContainer.getSession(jsessionid)
                .orElseGet(this::initSession);
    }

    private HttpSession initSession() {
        HttpSession session = HttpUUIDSession.of();
        SessionContainer.register(session);
        return session;
    }

    @Override
    public String getCookie(String key) {
        return getCookies().get(key);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", httpHeaders=" + httpHeaders +
                ", requestBody=" + requestBody +
                '}';
    }
}