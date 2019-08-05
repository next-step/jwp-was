package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("user password가 일치하는 경우")
    @Test
    void match_password() {
        User user = new User("Pooh", "password", "Pooh", "wow@wow");
        assertThat(user.isPasswordMatch("password")).isTrue();
    }

    @DisplayName("user password가 일치하는 않는 경우")
    @Test
    void not_match_password() {
        User user = new User("Pooh", "password", "Pooh", "wow@wow");
        assertThat(user.isPasswordMatch("wordpass")).isFalse();
    }
}