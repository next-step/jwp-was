package service;

import java.io.IOException;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    @DisplayName("회원가입 성공시, index.html 로 Redirect 된다.")
    @Test
    void createUser() {
        // given
        String userId = "javajigi";
        String password = "P@ssw0rD";
        String name = "JaeSung";
        String email = "javajigi@slipp.net";
        User user = new User(
                userId,
                password,
                name,
                email
        );

        // when
        Response response = UserService.createUser(user);
        String actual = response.toString();

        //then
        String expected = "HTTP/1.1 302 Found \r\n"
                + "Location: /index.html \r\n"
                + "\r\n";
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("유저목록 조회 요청시 로그인이 되어 있지 않으면, login.html 로 Redirect 된다.")
    @Test
    void getUserListNotLoggedIn() throws IOException {
        // given
        boolean loggedIn = false;

        // when
        Response response = UserService.getUserList(loggedIn);
        String actual = response.toString();

        //then
        String expected = "HTTP/1.1 302 Found \r\n"
                + "Location: /user/login.html \r\n"
                + "\r\n";
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("유저목록 조회 요청시 로그인이 되어 있다면, 200 을 응답한다.")
    @Test
    void getUserListSuccess() throws IOException {
        // given
        boolean loggedIn = true;

        // when
        Response response = UserService.getUserList(loggedIn);
        String actual = response.toString();

        //then
        assertThat(actual.contains("200 OK")).isTrue();
    }
}
