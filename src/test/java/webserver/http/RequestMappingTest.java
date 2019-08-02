/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver.http;

import org.junit.jupiter.api.Test;
import request.RequestHeader;
import request.RequestLine;
import response.Response;
import webserver.Controller;
import webserver.RequestMappingHandler;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class RequestMappingTest {

    @Test
    void index_request_mapping_test() {
        RequestLine requestLine = RequestLine.parse("GET /index.html HTTP/1.1");
        RequestHeader requestHeader = new RequestHeader(requestLine);
        Response response = new RequestMappingHandler(new Controller()).request(requestHeader);
        String stringBody = new String(response.getBody());

        assertThat(stringBody).isNotEmpty();
    }

    @Test
    void main_request_mapping_test() {
        RequestLine requestLine = RequestLine.parse("GET / HTTP/1.1");
        RequestHeader requestHeader = new RequestHeader(requestLine);
        Response response = new RequestMappingHandler(new Controller()).request(requestHeader);
        String stringBody = new String(response.getBody());

        assertThat(stringBody).isNotEmpty();
    }
}
