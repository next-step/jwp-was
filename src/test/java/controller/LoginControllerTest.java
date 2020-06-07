package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.StatusCode;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testutils.FileLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.LoginUtil.LOGGED_IN;

class LoginControllerTest {
    private final AbstractController CONTROLLER = new LoginController();
    private HttpResponse httpResponse;

    @BeforeEach
    void init() {
        httpResponse = HttpResponse.init();
        DataBase.deleteAll();
    }

    @Test
    @DisplayName("/user/login get요청 응답 확인")
    void get() throws IOException {
        InputStream inputStream = FileLoader.load("user-login-get.txt");
        HttpRequest httpRequest = HttpRequest.readRawRequest(inputStream);

        CONTROLLER.process(httpRequest, httpResponse);

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.REDIRECT);
        assertThat(httpResponse.getLocation()).isEqualTo("/login.html");
    }

    @Test
    @DisplayName("/user/login post요청 응답 확인 - 있는 유저일 경우")
    void post() throws IOException {
        InputStream inputStream = FileLoader.load("user-login-post.txt");
        HttpRequest httpRequest = HttpRequest.readRawRequest(inputStream);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "test");
        parameters.put("password", "1234");
        DataBase.addUser(User.init(parameters));

        CONTROLLER.process(httpRequest, httpResponse);

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.REDIRECT);
        assertThat(httpResponse.getLocation()).isEqualTo("/index.html");
        assertThat(httpResponse.getCookies().get(LOGGED_IN)).isEqualTo("true");
    }

    @Test
    @DisplayName("/user/login post요청 응답 확인 - 없는 유저일 경우")
    void postUserNotExist() throws IOException {
        InputStream inputStream = FileLoader.load("user-login-post.txt");
        HttpRequest httpRequest = HttpRequest.readRawRequest(inputStream);

        CONTROLLER.process(httpRequest, httpResponse);

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.REDIRECT);
        assertThat(httpResponse.getLocation()).isEqualTo("/user/login_failed.html");
        assertThat(httpResponse.getCookies().get(LOGGED_IN)).isEqualTo("false");
    }
}