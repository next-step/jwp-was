package webserver.response.post;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.response.post.PostUserLoginResponse.LOGIN_REDIRECT_HTML;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import db.DataBase;
import model.User;

class PostUserLoginResponseTest {

    @DisplayName("로그인 성공 시 로그인 페이지로 이동해야 한다.")
    @Test
    void loginSuccess() {
        // given
        DataBase.addUser(new User(
                "test",
                "test",
                "테스트",
                "test@email.com"
        ));

        PostUserLoginResponse response = new PostUserLoginResponse();

        // when
        boolean actual = response.response("userId=test&password=test");

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("로그인 실패 시 로그인 실패 페이지로 이동해야 한다.")
    @CsvSource(value = {"userId=inavlid&password=test", "userId=test&password=x"})
    @ParameterizedTest
    void loginFailed(String requestBody) {
        // given
        PostUserLoginResponse response = new PostUserLoginResponse();

        // when
        boolean actual = response.response(requestBody);

        // then
        assertThat(actual).isFalse();
    }
}