/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver;

import request.HttpMethod;
import request.RequestLine;
import utils.FileIoUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static request.HttpMethod.GET;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public enum HttpRequestMapping implements RequestMapping {
    MAIN(GET, Arrays.asList("", "/")) {
        @Override
        public byte[] getBody() {
            return "Hello World".getBytes();
        }
    },
    INDEX(GET, Collections.singletonList("/index.html")) {
        @Override
        public byte[] getBody() {
            try {
                return FileIoUtils.loadFileFromClasspath("./templates/index.html");
            } catch (Exception e) {
                throw new RuntimeException("read index file exception: ", e);
            }
        }
    };

    private HttpMethod httpMethod;
    private List<String> path;

    HttpRequestMapping(HttpMethod httpMethod, List<String> path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }

    public static HttpRequestMapping get(RequestLine requestLine) {
        return Arrays.stream(HttpRequestMapping.values())
                .filter(httpRequestMapping -> httpRequestMapping.isMatching(requestLine))
                .findAny()
                .orElseThrow(() -> new RuntimeException(""));
    }

    private boolean isMatching(RequestLine requestLine) {
        return this.httpMethod == requestLine.getMethod() && this.path.contains(requestLine.getPath());
    }

}
