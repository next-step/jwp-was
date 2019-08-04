/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver.http;

import org.junit.jupiter.api.Test;
import request.HttpRequest;
import request.RequestLine;
import response.HttpResponse;
import controller.Controller;
import handler.RequestMappingHandler;
import response.HttpStatus;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class RequestStrategyMappingTest {

    @Test
    void index_request_mapping_test() {
        RequestLine requestLine = RequestLine.parse("GET /index.html HTTP/1.1");
        HttpRequest httpRequest = new HttpRequest(requestLine);
        HttpResponse httpResponse = new RequestMappingHandler(new Controller()).request(httpRequest);
        String stringBody = new String(httpResponse.getBody());

        assertThat(stringBody).isNotEmpty();
    }

    @Test
    void main_request_mapping_test() {
        RequestLine requestLine = RequestLine.parse("GET / HTTP/1.1");
        HttpRequest httpRequest = new HttpRequest(requestLine);
        HttpResponse httpResponse = new RequestMappingHandler(new Controller()).request(httpRequest);
        String stringBody = new String(httpResponse.getBody());

        assertThat(stringBody).isNotEmpty();
    }

    @Test
    void login_failed_mapping_test() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("GET /user/list.html HTTP/1.1\n" +
                "Cookie: logined=false\n"));
        HttpRequest httpRequest = new HttpRequest(bufferedReader);
        HttpResponse httpResponse = new RequestMappingHandler(new Controller()).request(httpRequest);
        String stringBody = new String(httpResponse.getBody());

        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(stringBody).isNotEmpty();
    }
}
