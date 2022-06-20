package controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {

    @BeforeEach
    void setUp() {
        DataBase.clear();
    }

    @Test
    void serving() throws IOException, URISyntaxException {
        DataBase.addUser(new User(
                "javajigi",
                "P@ssw0rD",
                "JaeSung",
                "javajigi@slipp.net"
        ));

        RequestLine requestLine = new RequestLine("GET /user/login?userId=javajigi&password=P@ssw0rD HTTP/1.1");

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Accept", "*/*");


        Request request = new Request(
                requestLine,
                headers,
                null);

        Response response = DispatcherServlet.match(request);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getContentType()).isEqualTo("text/html;charset=utf-8");
        assertThat(response.getPath()).isEqualTo("/index.html");
        assertThat(response.getCookie()).isEqualTo("logined=true; Path=/");

        String result = new String(response.getBytes());

        assertThat(result.indexOf("Set-Cookie: logined=true; Path=/") != -1).isTrue();
    }

    @Test
    void serving_fail() throws IOException, URISyntaxException {
        RequestLine requestLine = new RequestLine("GET /user/login?userId=test&password=111 HTTP/1.1");

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Accept", "*/*");


        Request request = new Request(
                requestLine,
                headers,
                null);

        Response response = DispatcherServlet.match(request);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getContentType()).isEqualTo("text/html;charset=utf-8");
        assertThat(response.getPath()).isEqualTo("/user/login_failed.html");
        assertThat(response.getCookie()).isEqualTo("logined=false; Path=/");

        String result = new String(response.getBytes());

        assertThat(result.indexOf("Set-Cookie: logined=false; Path=/") != -1).isTrue();
    }
}
