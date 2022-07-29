package webserver.service;

import db.DataBase;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Cookie;
import webserver.controller.AbstractController;
import webserver.request.HelpData;
import webserver.request.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    LoginService loginService;
    HttpRequest request;


    @BeforeEach
    void setup() throws IOException {
        DataBase.clear();
        BufferedReader br = HelpData.postHelpData();
        request = HttpRequest.parsing(br);
        loginService = new LoginService(request);

        DataBase.addUser(new User("java", "password", "name", "java@java.com"));
    }

    @Test
    @DisplayName("유저의 패스워드 아이디가 일치할 시 쿠기값 true")
    void check_id_password_cookie_true() {
        User requestUser = new User("java", "password", "name", "email");

        Cookie cookie = loginService.checkIdAndPassword(requestUser);
        assertThat(cookie.getCookie("logined")).isEqualTo("true");
    }

    @Test
    @DisplayName("인증된 유저가 로그인 시 index.html로 리다이렉트 된다.")
    void authorized_user() {
        Cookie cookie = new Cookie();
        cookie.setCookie(AbstractController.LOGINED_KEY, "true");

        assertThat(loginService.getRedirectUrl(cookie)).isEqualTo(AbstractController.INDEX_URl);
    }
}