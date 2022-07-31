package webserver.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import http.HttpStatus;
import http.request.Headers;
import http.request.HttpRequest;
import http.request.RequestLine;
import model.User;

class LoginControllerTest {

    @BeforeEach
    void setUp() {
        DataBase.clear();
    }

    @DisplayName("로그인 성공 테스트")
    @Test
    void run() {
        DataBase.addUser(new User("user", "1234", "testname", "email@naver.com"));

        var body = "userId=user&password=1234";
        var httpRequest = new HttpRequest(
            new RequestLine("POST /user/login HTTP/1.1"),
            new Headers(List.of("Content-Length: " + body.length())),
            body
        );

        var controller = new LoginController();
        var response = controller.service(httpRequest);

        assertAll(
            ()-> assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND),
            ()-> assertThat(response.getHeaders().get("Location")).isEqualTo("/index.html"),
            ()-> assertThat(response.getCookie("isLogined").get()).isEqualTo("true")
        );
    }
}