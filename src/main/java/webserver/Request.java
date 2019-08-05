package webserver;

import utils.IOUtils;
import webserver.request.Cookie;
import webserver.request.RequestBody;
import webserver.request.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static webserver.HttpHeaders.ACCEPT;
import static webserver.HttpHeaders.COOKIE;

public class Request {

    private static final String UTF_8 = "UTF-8";
    private static final int DEFAULT_OF_CONTENT_LENGTH = -1;

    private RequestLine requestLine;
    private HttpHeaders httpHeaders;
    private RequestBody requestBody;

    Request(RequestLine requestLine, HttpHeaders httpHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.requestBody = requestBody;
    }

    static Request of(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, UTF_8));
        RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
        HttpHeaders httpHeaders = generateHeaders(bufferedReader);

        if (httpHeaders.getContentLength() > DEFAULT_OF_CONTENT_LENGTH) {
            String requestBody = IOUtils.readData(bufferedReader, httpHeaders.getContentLength());
            return new Request(requestLine, httpHeaders, RequestBody.parse(requestBody));
        }
        return new Request(requestLine, httpHeaders, RequestBody.EMPTY);
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

    public String getParameter(String field) {
        return requestBody.get(field);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Cookie getCookie() {
        return Cookie.of(httpHeaders.get(COOKIE));
    }

    public String getAccept() {
        return httpHeaders.get(ACCEPT).split(",")[0];
    }

    public boolean matchPath(String path) {
        return requestLine.matchPath(path);
    }

    public boolean containPath(String path) {
        return requestLine.containPath(path);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestLine=" + requestLine +
                ", httpHeaders=" + httpHeaders +
                ", requestBody=" + requestBody +
                '}';
    }
}