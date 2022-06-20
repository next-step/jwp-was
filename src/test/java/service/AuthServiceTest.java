package service;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthServiceTest {


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

    @DisplayName("userId 에 해당되는 유저의 password 가 일치하면, 로그인이 성공하고 true 를 반환한다.")
    @Test
    void loginSuccess() {
        String userId = "javajigi";
        String password = "P@ssw0rD";
        assertThat(
                AuthService.login(userId, password)
        ).isTrue();
    }

    @DisplayName("userId 에 해당되는 유저가 존재하지 않으면, 로그인이 실패하고 false 를 반환한다.")
    @Test
    void loginUserNotFound() {
        String userId = "WRONG_USER_ID";
        String password = "P@ssw0rD";
        assertThat(
                AuthService.login(userId, password)
        ).isFalse();
    }

    @DisplayName("userId 에 해당되는 유저의 password 가 일치하지 않으면, 로그인이 실패하고 false 를 반환한다.")
    @Test
    void loginWrongPassword() {
        String userId = "javajigi";
        String password = "WRONG_PASSWORD";
        assertThat(
                AuthService.login(userId, password)
        ).isFalse();
    }
}
