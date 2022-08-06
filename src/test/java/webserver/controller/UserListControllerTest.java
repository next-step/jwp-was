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
import http.request.session.MemorySessionStore;
import model.User;

class UserListControllerTest {

    @BeforeEach
    void setUp() {
        DataBase.clear();
    }

    @Test
    void run() {
        DataBase.addUser(new User("1", "1234", "pobi", "test@naver.com"));
        DataBase.addUser(new User("2", "1234", "hong", "test1@naver.com"));
        DataBase.addUser(new User("3", "1234", "bin", "test2@naver.com"));

        var sessionStore = new MemorySessionStore();
        sessionStore.fetch("key").setAttribute("isLogined", true);

        var httpRequest = new HttpRequest(new RequestLine("GET /user/list HTTP/1.1"),
            new Headers(List.of("Content-type: text/html", "Cookie: SESSION_ID=key")), "", sessionStore);

        var controller = new UserListController();
        var response = controller.service(httpRequest);

        assertAll(
            () -> assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK),
            () -> assertThat(response.getBody()).contains("1", "pobi", "test@naver.com"),
            () -> assertThat(response.getBody()).contains("2", "hong", "test1@naver.com"),
            () -> assertThat(response.getBody()).contains("3", "bin", "test2@naver.com")
        );

    }
}