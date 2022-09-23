package model;

import exception.ValidateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("사용자 객체 테스트")
class UserTest {

    @Test
    @DisplayName("올바른 값이 입력될 경우 User객체를 생성한다.")
    void parse() {
        String userId = "javajigi";
        String password = "password";
        String name = "JaeSung";
        String email = "javajigi@slipp.net";

        User actual = new User(userId, password, name, email);

        assertThat(actual.getUserId()).isEqualTo(userId);
        assertThat(actual.getPassword()).isEqualTo(password);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getEmail()).isEqualTo(email);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("올바르지 않은 값이 입력될 경우 ValidateException 이 발생한다.")
    void validate(String name) {
        assertThatThrownBy(() -> new User("javajigi", "password", name, "javajigi@slipp.net"))
                .isInstanceOf(ValidateException.class)
                .hasMessage("invalid argument: (%s)", name);
    }

    @Test
    @DisplayName("입력된 비밀번호와 사용자의 비밀번호가 같으면 True를 리턴한다.")
    void verifyPasswordIsTrue() {
        User user = new User("javajigi", "password", "JaeSung", "javajigi@slipp.net");

        boolean actual = user.verifyPassword("password");

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("입력된 비밀번호와 사용자의 비밀번호가 다르면 False를 리턴한다.")
    void verifyPasswordIsFalse() {
        User user = new User("javajigi", "password", "JaeSung", "javajigi@slipp.net");

        boolean actual = user.verifyPassword("test-password");

        assertThat(actual).isFalse();
    }
}
