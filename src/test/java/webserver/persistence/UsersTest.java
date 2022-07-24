package webserver.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.User;

class UsersTest {

    @Test
    void save() {
        var users = new Users();

        assertDoesNotThrow(
            () -> users.save("1", new User("1", "1234", "hong", "hong@naver.com"))
        );
    }

    @Test
    void findBy() {
        var users = new Users();
        users.save("1", new User("1", "1234", "hong", "hong@naver.com"));

        var user = users.findBy("1");

        var savedUser = user.get();
        assertAll(
            () -> assertThat(savedUser.getUserId()).isEqualTo("1"),
            () -> assertThat(savedUser.getEmail()).isEqualTo("hong@naver.com"),
            () -> assertThat(savedUser.getName()).isEqualTo("hong"),
            () -> assertThat(savedUser.getPassword()).isEqualTo("1234")
        );
    }
}