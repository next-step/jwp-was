package webserver.http.request;

import webserver.http.Header;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class HttpRequest implements Request {
    private final RequestLine requestLine;

    private final Header header;

    private final RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, Header header, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.header = header;
        this.requestBody = requestBody;
    }

    public static HttpRequest of(BufferedReader bufferedReader) throws IOException {
        RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
        Header header = Header.of(bufferedReader);
        RequestBody requestBody = RequestBody.of(bufferedReader, header.getContentLength());

        return new HttpRequest(requestLine, header, requestBody);
    }

    @Override
    public String getPath() {
        return requestLine.getUri().getPath();
    }

    @Override
    public Method getMethod() {
        return requestLine.getMethod();
    }

    @Override
    public RequestParameters getRequestParameters() {
        return requestLine.getUri().getRequestParameters();
    }

    @Override
    public RequestBody getRequestBody() {
        return requestBody;
    }

    @Override
    public String getHeader(String key) {
        return header.get(key);
    }

    @Override
    public String getCookie(String key) {
        return header.getCookie(key);
    }

    public boolean isGet() {
        return getMethod() == Method.GET;
    }

    public boolean isPost() {
        return getMethod() == Method.POST;
    }

    public Protocol getProtocol() {
        return requestLine.getProtocol();
    }

    public Version getVersion() {
        return requestLine.getVersion();
    }

    public boolean isLogined() {
        String logined = header.getCookie("logined");
        if (Objects.nonNull(logined) && logined.equals("true")) {
            return true;
        }
        return false;
    }
}
