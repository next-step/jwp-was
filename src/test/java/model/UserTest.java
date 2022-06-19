package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    User user;

    @BeforeEach
    void setUp() {
        String userId = "javajigi";
        String password = "P@ssw0rD";
        String name = "JaeSung";
        String email = "javajigi@slipp.net";
        user = new User(userId, password, name, email);
    }

    @DisplayName("비밀번호가 같으면 true 를 반환해야한다.")
    @Test
    void matchPasswordTrue() {
        assertThat(user.matchPassword("P@ssw0rD")).isTrue();
    }

    @DisplayName("비밀번호가 다르면 false 를 반환해야한다.")
    @Test
    void matchPasswordFalse() {
        assertThat(user.matchPassword("pw")).isFalse();
    }
}
