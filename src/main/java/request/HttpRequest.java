/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package request;

import header.Cookie;
import header.setter.HeaderSetter;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import session.HttpSession;
import session.SessionManager;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static response.HeaderResponse.KEY_VALUE_SPLITER;
import static session.SessionIdGenerator.SESSION_ID_NAME;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class HttpRequest {
    @RequestHeaderProperty(converter = RequestLine.class)
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

    @RequestHeaderProperty(value = "Cookie", converter = Cookie.class)
    private Cookie cookie = new Cookie();

    @RequestHeaderProperty("Content-Type")
    private String contentType;

    @RequestHeaderProperty("Content-Length")
    private String contentLength;

    @RequestHeaderProperty
    private String body;

    private MultiValueMap<String, String> bodyMap;

    @Nullable
    private volatile HttpSession session;

    public HttpRequest(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public HttpRequest(BufferedReader bufferedReader) throws Exception {
        setHeaders(bufferedReader);
        setBodyIfNotGet(bufferedReader);
    }

    private void setHeaders(BufferedReader bufferedReader) throws IOException, IllegalAccessException, InstantiationException {
        String line;
        boolean isRequestLine = true;
        while ((line = bufferedReader.readLine()) != null && !StringUtils.isEmpty(line)) {
            String[] keyValue = line.split(KEY_VALUE_SPLITER);
            boolean finalIsRequestLine = isRequestLine;
            Optional<Field> optionalField = Arrays.stream(this.getClass().getDeclaredFields())
                    .filter(f -> {
                        RequestHeaderProperty[] propertys = f.getAnnotationsByType(RequestHeaderProperty.class);
                        return finalIsRequestLine || (propertys.length > 0 && propertys[0].value().equalsIgnoreCase(keyValue[0]));
                    })
                    .findAny();

            if (!optionalField.isPresent()) {
                continue;
            }

            Field field = optionalField.get();
            field.setAccessible(true);
            HeaderSetter headerSetter = field.getAnnotationsByType(RequestHeaderProperty.class)[0].converter().newInstance();
            field.set(this, headerSetter.setEliment(keyValue));
            isRequestLine = false;
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

    public Cookie getCookie() {
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

    public HttpRequest setIfExistSession(SessionManager sessionManager) {
        if (cookie.isLogined()) {
            String sessionId = cookie.get(SESSION_ID_NAME);
            session = sessionManager.getSession(sessionId);
        }
        return this;
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
