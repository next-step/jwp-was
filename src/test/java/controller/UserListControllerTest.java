package controller;

import org.junit.jupiter.api.Test;
import webserver.http.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest {

    @Test
    void serving() throws Exception {
        RequestLine requestLine = new RequestLine("GET /user/list.html HTTP/1.1");

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Cookie", "logined=true; Path=/");
        headers.put("Accept", "*/*");


        HttpRequest httpRequest = new HttpRequest(
                requestLine,
                headers,
                null);

        HttpResponse httpResponse = new UserListMappingController().service(httpRequest);

        assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getContentType()).isEqualTo(MediaType.TEXT_HTML_UTF8);
        assertThat(httpResponse.getPath()).isEqualTo("/user/list.html");
    }

    @Test
    void serving_unauthorized() throws Exception {
        RequestLine requestLine = new RequestLine("GET /user/list.html HTTP/1.1");

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Cookie", "logined=false; Path=/");
        headers.put("Accept", "*/*");


        HttpRequest httpRequest = new HttpRequest(
                requestLine,
                headers,
                null);

        HttpResponse httpResponse =  new UserListMappingController().service(httpRequest);

        assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(httpResponse.getContentType()).isEqualTo(MediaType.TEXT_HTML_UTF8);
        assertThat(httpResponse.getPath()).isEqualTo("/index.html");
    }
}
