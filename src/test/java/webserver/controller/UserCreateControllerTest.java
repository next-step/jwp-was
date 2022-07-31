package webserver.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.DataBase;
import http.HttpStatus;
import http.request.Headers;
import http.request.HttpRequest;
import http.request.RequestLine;

class UserCreateControllerTest {

    @BeforeEach
    void setUp() {
        DataBase.clear();
    }

    @Test
    void run() {
        var body = "userId=user&password=1234&email=test@naver.com&name=pobi";
        var httpRequest = new HttpRequest(
            new RequestLine("POST /user/create HTTP/1.1"),
            new Headers(List.of("Content-Length: " + body.length())),
            body
        );

        var controller = new UserCreateController();
        var response = controller.service(httpRequest);

        var actual = DataBase.findUserById("user")
                .get();

        assertAll(
            ()-> assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND),
            ()-> assertThat(response.getHeaders().get("Location")).isEqualTo("/index.html"),
            ()-> assertThat(actual.getPassword()).isEqualTo("1234"),
            ()-> assertThat(actual.getEmail()).isEqualTo("test@naver.com"),
            ()-> assertThat(actual.getName()).isEqualTo("pobi")
        );
    }
}