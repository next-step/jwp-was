package controller;

import org.junit.jupiter.api.Test;
import webserver.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest {

    @Test
    void serving() throws IOException, URISyntaxException {
        RequestLine requestLine = new RequestLine("GET /user/list.html HTTP/1.1");

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Cookie", "logined=true; Path=/");
        headers.put("Accept", "*/*");


        Request request = new Request(
                requestLine,
                headers,
                null);

        Response response = DispatcherServlet.match(request);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getContentType()).isEqualTo("text/html;charset=utf-8");
        assertThat(response.getPath()).isEqualTo("/user/list.html");
    }

    @Test
    void serving_unauthorized() throws IOException, URISyntaxException {
        RequestLine requestLine = new RequestLine("GET /user/list.html HTTP/1.1");

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Cookie", "logined=false; Path=/");
        headers.put("Accept", "*/*");


        Request request = new Request(
                requestLine,
                headers,
                null);

        Response response = DispatcherServlet.match(request);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getContentType()).isEqualTo("text/html;charset=utf-8");
        assertThat(response.getPath()).isEqualTo("/index.html");
    }
}
