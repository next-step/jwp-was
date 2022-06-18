package service;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

class LoginServiceTest {


    @BeforeEach
    void setUp() {
        DataBase.clear();
        User user = new User(
                "javajigi",
                "P@ssw0rD",
                "JaeSung",
                "javajigi@slipp.net"
        );
        DataBase.addUser(user);
    }

    @DisplayName("로그인이 성공하면, 쿠키 값이 loggedIn=true 가 되고, index.html 로 Redirect 한다.")
    @Test
    void doGetLoginSuccess() {
        // given
        String userId = "javajigi";
        String password = "P@ssw0rD";

        // when
        Response response = LoginService.login(userId, password);
        String actual = response.toString();

        //then
        String expected = "HTTP/1.1 302 Found \r\n"
                + "Location: /index.html \r\n"
                + "Set-Cookie: loggedIn=true \r\n"
                + "Content-Length: 0 \r\n"
                + "\r\n";
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("로그인이 실패하면, 쿠키 값이 loggedIn=false 가 되고, login_failed.html 로 Redirect 한다.")
    @Test
    void doPostLoginFailed() {
        // given
        String userId = "id";
        String password = "pw";

        // when
        Response response = LoginService.login(userId, password);
        String actual = response.toString();

        //then
        String expected = "HTTP/1.1 302 Found \r\n"
                + "Location: /login_failed.html \r\n"
                + "Set-Cookie: loggedIn=false \r\n"
                + "Content-Length: 0 \r\n"
                + "\r\n";
        assertThat(actual).isEqualTo(expected);
    }
}
