package controller;

import db.DataBase;
import http.HttpRequest;
import http.RequestLineParser;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("유저 컨트롤러")
class UserControllerTest {
    private static final String RAW_REQUEST =
            " /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*";
    private static final String RAW_GET_REQUEST = "GET" + RAW_REQUEST;
    private static final String RAW_POST_REQUEST = "POST" + RAW_REQUEST;

    private static final UserController USER_CONTROLLER = new UserController();

    @Test
    @DisplayName("/user/create 호출시 유저를 메모리에 저장한다.")
    void create() {
        HttpRequest httpRequest = HttpRequest.init(RequestLineParser.parse(RAW_GET_REQUEST));
        Collection<User> users = DataBase.findAll();

        assertThat(users).isEmpty();

        USER_CONTROLLER.process(httpRequest);

        assertThat(DataBase.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("/user/create POST 호출시 유저를 메모리에 저장한다.")
    void createUsingPost() {
        HttpRequest httpRequest = HttpRequest.init(RequestLineParser.parse(RAW_POST_REQUEST));
        Collection<User> users = DataBase.findAll();

        assertThat(users).isEmpty();

        USER_CONTROLLER.process(httpRequest);

        assertThat(DataBase.findAll()).hasSize(1);
    }
}