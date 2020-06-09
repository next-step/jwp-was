package http.request;

import http.common.HttpEntity;
import http.common.HttpHeaders;
import http.common.HttpMethod;
import http.request.requestline.Protocol;
import http.request.requestline.RequestLine;
import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static http.common.HttpHeader.CONTENT_LENGTH_NAME;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpEntity httpEntity;

    public HttpRequest(RequestLine requestLine, HttpEntity httpEntity) {
        this.requestLine = requestLine;
        this.httpEntity = httpEntity;
    }

    public static HttpRequest of(BufferedReader br, RequestLine requestLine, HttpHeaders httpHeaders) throws IOException {
        if (HttpMethod.GET.equals(requestLine.getMethod())) {
            return HttpRequest.ofGet(requestLine, httpHeaders);
        }

        String httpBody = getHttpBody(br, StringUtils.toInt(httpHeaders.getHeaderValue(CONTENT_LENGTH_NAME)));
        return HttpRequest.ofPost(requestLine, httpHeaders, httpBody);
    }

    private static String getHttpBody(BufferedReader br, int contentLength) throws IOException {
        char[] buffer = new char[contentLength];
        br.read(buffer, 0, contentLength);

        String httpBody = new String(buffer, 0, buffer.length);
        return httpBody;
    }

    private static HttpRequest ofGet(
        RequestLine requestLine,
        HttpHeaders httpHeaders
    ) {
        return new HttpRequest(requestLine, new HttpEntity(httpHeaders));
    }

    private static HttpRequest ofPost(
        RequestLine requestLine,
        HttpHeaders httpHeaders,
        String httpBody
    ) {
        return new HttpRequest(requestLine, new HttpEntity(httpHeaders, httpBody));
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public Protocol getProtocol() {
        return requestLine.getProtocol();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Map<String, String> getQueryString() {
        return requestLine.getQueryString();
    }

    public String getQueryStringValue(String key) {
        return Optional.ofNullable(requestLine)
            .map(RequestLine::getQueryString)
            .map(map -> map.get(key))
            .orElse(null);
    }

    public Map<String, String> getBodyMap() {
        return Optional.ofNullable(httpEntity)
            .map(HttpEntity::getBodyMap)
            .orElse(Collections.emptyMap());
    }

    public String getBodyMapValue(String key) {
        return getBodyMap().get(key);
    }

    public String getHeaderValue(String key) {
        return httpEntity.getHeaderValue(key);
    }
}
