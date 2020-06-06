package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 테스트")
class UserTest {

    @DisplayName("비밀번호 일치 여부를 검사한다.")
    @Test
    void checkPassword() {
        User user = new User("serverwizard", "password", "hong", "serverwizard@woowahan.com");

        assertThat(user.matchPassword("password")).isTrue();
        assertThat(user.matchPassword("password2")).isFalse();
    }

}
