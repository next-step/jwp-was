package webserver.service;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HelpData;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class LoginServiceTest {
    LoginService loginService;
    HttpRequest request;
    HttpResponse response;

    @BeforeEach
    void setup() throws IOException {
        DataBase.clear();
        BufferedReader br = HelpData.postHelpData();
        request = HttpRequest.parsing(br);
        response = new HttpResponse();
        loginService = new LoginService(request, response);

        DataBase.addUser(new User("java", "password", "name", "java@java.com"));
    }

    @Test
    @DisplayName("유저의 패스워드 아이디가 일치할 시 쿠기값 true")
    void check_id_password_cookie_true() {
        User requestUser = new User("java", "password", "name", "email");

        loginService.checkIdAndPassword(requestUser);

        assertThat(request.getSession().size()).isEqualTo(1);
        assertThat((String) request.getSession().getAttribute("logined")).isEqualTo("true");
        assertThat(response.getCookie().getCookie("sessionId")).isNotNull();
    }

    @Test
    @DisplayName("인증된 유저가 로그인 시 index.html로 리다이렉트 된다.")
    void authorized_user() {
        User requestUser = new User("java", "password", "name", "email");

        loginService.checkIdAndPassword(requestUser);
        String locationPath = response.getLocationPath();
        assertThat(locationPath).isEqualTo("index.html");
    }
}