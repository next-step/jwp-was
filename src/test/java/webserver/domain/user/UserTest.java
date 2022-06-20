package webserver.domain.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    public void create() {
        String userId = "javajigi";
        String password = "1234";
        String name = "자바지기";
        String email = "javajigi@gmail.com";

        User actual = new User(userId, password, name, email);

        assertThat(actual).isEqualTo(new User(userId, password, name, email));
    }
}
