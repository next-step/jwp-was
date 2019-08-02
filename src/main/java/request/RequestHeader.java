/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package request;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.util.Arrays;
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

    private String body;

    public RequestHeader(BufferedReader bufferedReader) throws Exception {
        boolean isRequestLine = true;
        String line;
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

        String body = bufferedReader.readLine();
        if (!StringUtils.isEmpty(body)) { //TODO:content-Legth 체크
            this.body = body;
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

    public String getBody() {
        return body;
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
                '}';
    }
}
