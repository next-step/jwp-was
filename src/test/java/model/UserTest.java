package model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserTest {

    @Test
    void 사용자_비밀번호_확인() {
        String password = "password1234";
        User user = new User("userId", password, "name", "test@test.com");

        assertAll(
                () -> assertThat(user.checkPassword(password)).isTrue(),
                () -> assertThat(user.checkPassword("")).isFalse()
        );
    }
}
