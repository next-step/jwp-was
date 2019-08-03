/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package request;

import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class RequestHeader {
    @RequestHeaderProperty("RequestLine")
    private RequestLine requestLine;

    @RequestHeaderProperty("HOST")
    private String host;

    @RequestHeaderProperty("Accept")
    private String accept;

    @RequestHeaderProperty("Accept-Language")
    private String acceptLanguage;

    @RequestHeaderProperty("Accept-Encoding")
    private String acceptEncoding;

    @RequestHeaderProperty("User-Agent")
    private String userAgent;

    @RequestHeaderProperty("Connection")
    private String connection;

    @RequestHeaderProperty("Cache-Control")
    private String cacheControl;

    @RequestHeaderProperty("Cookie")
    private String cookie;

    @RequestHeaderProperty("Content-Type")
    private String contentType;

    @RequestHeaderProperty("Content-Length")
    private String contentLength;

    @RequestHeaderProperty
    private String body;

    @RequestHeaderProperty
    private MultiValueMap<String, String> bodyMap;

    public RequestHeader(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public RequestHeader(BufferedReader bufferedReader) throws Exception {
        setHeaders(bufferedReader);
        setBodyIfNotGet(bufferedReader);
    }

    private void setHeaders(BufferedReader bufferedReader) throws IOException, IllegalAccessException {
        String line;
        boolean isRequestLine = true;
        while ((line = bufferedReader.readLine()) != null && !StringUtils.isEmpty(line)) {
            if (isRequestLine) {
                this.requestLine = RequestLine.parse(line);
                isRequestLine = false;
                continue;
            }

            String[] keyValue = line.split(":");
            Optional<Field> optionalField = Arrays.stream(this.getClass().getDeclaredFields())
                    .filter(f -> f.getAnnotationsByType(RequestHeaderProperty.class)[0].value().equalsIgnoreCase(keyValue[0]))
                    .findAny();

            if (!optionalField.isPresent()) {
                continue;
            }

            Field field = optionalField.get();
            field.setAccessible(true);
            field.set(this, keyValue[1]);
        }
    }

    private void setBodyIfNotGet(BufferedReader bufferedReader) throws IOException {
        if (HttpMethod.GET != requestLine.getMethod()) {
            String body = IOUtils.readData(bufferedReader, Integer.parseInt(Objects.requireNonNull(this.contentLength).trim()));
            if (!StringUtils.isEmpty(body)) {
                this.body = body;
                this.bodyMap = RequestLine.makeQueryString(body);
            }
        }
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public String getHost() {
        return host;
    }

    public String getAccept() {
        return accept;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getConnection() {
        return connection;
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public String getCookie() {
        return cookie;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContentLength() {
        return contentLength;
    }

    public String getBody() {
        return body;
    }

    public MultiValueMap<String, String> getBodyMap() {
        return bodyMap;
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "requestLine=" + requestLine +
                ", host='" + host + '\'' +
                ", accept='" + accept + '\'' +
                ", acceptLanguage='" + acceptLanguage + '\'' +
                ", acceptEncoding='" + acceptEncoding + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", connection='" + connection + '\'' +
                ", cacheControl='" + cacheControl + '\'' +
                ", cookie='" + cookie + '\'' +
                ", contentType='" + contentType + '\'' +
                ", contentLength='" + contentLength + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
