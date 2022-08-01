package utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.LoginUser;
import model.User;
import utils.exception.UserParserException;

class UserParserTest {

    @DisplayName("유저 생성시 올바르지 않은 형태의 경우 생성이 되지 않는다.")
    @Test
    void createFailedByRequestBody() {
        assertThatThrownBy(() -> UserParser.createUser("test"))
                .isInstanceOf(UserParserException.class);
    }

    @DisplayName("유저 생성 테스트")
    @Test
    void createUser() {
        User user = UserParser.createUser("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(user.getEmail()).isEqualTo("javajigi%40slipp.net");
    }

    @DisplayName("로그인 유저 생성 테스트")
    @Test
    void createLoginUser() {
        LoginUser loginUser = UserParser.createLoginUser("userId=javajigi&password=password");

        assertThat(loginUser.getUserId()).isEqualTo("javajigi");
        assertThat(loginUser.getPassword()).isEqualTo("password");
    }
}