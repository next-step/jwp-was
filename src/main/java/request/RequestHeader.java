/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package request;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.util.Arrays;

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
            Field field = Arrays.stream(this.getClass().getDeclaredFields())
                    .filter(f -> f.getAnnotationsByType(RequestHeaderProperty.class)[0].value().equalsIgnoreCase(keyValue[0]))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("It's not permmited header"));
            field.setAccessible(true);
            field.set(this, keyValue[1]);
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
}
