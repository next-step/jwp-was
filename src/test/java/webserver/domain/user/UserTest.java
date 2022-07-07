package webserver.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    final String userId = "javajigi";
    final String password = "1234";
    final String name = "자바지기";
    final String email = "javajigi@gmail.com";

    @Test
    public void create() {
        User actual = new User(userId, password, name, email);

        assertThat(actual).isEqualTo(new User(userId, password, name, email));
    }

    @DisplayName("패스워드 검증을 통해 로그인 ")
    @Test
    public void verifyPassword() {
        User user = new User(userId, password, name, email);

        boolean actual = user.verifyPassword("1234");

        assertThat(actual).isTrue();
    }
}
