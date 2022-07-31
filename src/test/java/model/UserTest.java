package model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void canLogin() {
        var user = new User("1", "1234", "test", "test@naver.com");

        var actual = user.canLogin("1234");

        assertThat(actual).isTrue();
    }
}