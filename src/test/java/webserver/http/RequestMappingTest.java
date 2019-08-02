/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver.http;

import org.junit.jupiter.api.Test;
import request.RequestLine;
import webserver.Controller;
import webserver.HttpRequestMapping;
import webserver.RequestMapping;
import webserver.RequestMappingHandler;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class RequestMappingTest {

    @Test
    void index_request_mapping_test() {
        RequestLine requestLine = RequestLine.parse("GET /index.html HTTP/1.1");
        byte[] body = new RequestMappingHandler(new Controller()).getBody(requestLine);
        String stringBody = new String(body);

        assertThat(stringBody).isNotEmpty();
    }

    @Test
    void main_request_mapping_test() {
        RequestLine requestLine = RequestLine.parse("GET / HTTP/1.1");
        RequestMapping httpRequestMapping = HttpRequestMapping.get(requestLine);
        byte[] body = httpRequestMapping.getBody();
        String stringBody = new String(body);

        assertThat(stringBody).isNotEmpty();
    }
}
