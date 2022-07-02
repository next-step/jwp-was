package controller;

import db.DataBase;
import http.*;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {

    @BeforeEach
    void setUp() {
        DataBase.clear();
    }

    @Test
    void match() throws Exception {
        DataBase.addUser(new User(
                "javajigi",
                "P@ssw0rD",
                "JaeSung",
                "javajigi@slipp.net"
        ));

        RequestLine requestLine = new RequestLine("GET /user/login?userId=javajigi&password=P@ssw0rD HTTP/1.1");

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Accept", "*/*");


        HttpRequest httpRequest = new HttpRequest(
                requestLine,
                headers,
                null);

        HttpResponse httpResponse = new LoginMappingController().service(httpRequest);

        assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(httpResponse.getContentType()).isEqualTo(MediaType.TEXT_HTML_UTF8);
        assertThat(httpResponse.getPath()).isEqualTo("/index.html");
        assertThat(httpResponse.getCookie()).isEqualTo("logined=true; Path=/");

        String result = new String(httpResponse.getBytes());

        assertThat(result.indexOf("Set-Cookie: logined=true; Path=/") != -1).isTrue();
    }

    @Test
    void match_fail() throws Exception {
        RequestLine requestLine = new RequestLine("GET /user/login?userId=test&password=111 HTTP/1.1");

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Accept", "*/*");


        HttpRequest httpRequest = new HttpRequest(
                requestLine,
                headers,
                null);

        HttpResponse httpResponse = new LoginMappingController().service(httpRequest);

        assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(httpResponse.getContentType()).isEqualTo(MediaType.TEXT_HTML_UTF8);
        assertThat(httpResponse.getPath()).isEqualTo("/user/login_failed.html");
        assertThat(httpResponse.getCookie()).isEqualTo("logined=false; Path=/");

        String result = new String(httpResponse.getBytes());

        assertThat(result.indexOf("Set-Cookie: logined=false; Path=/") != -1).isTrue();
    }
}
