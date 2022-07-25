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

    @Test
    void findAll() {
        var users = new Users();
        var user1 = new User("1", "1234", "hong", "hong@naver.com");
        var user2 = new User("2", "1234", "hong2", "hong2@naver.com");

        users.save("1", user1);
        users.save("2", user2);

        var all = users.findAll();

        assertThat(all).contains(user1, user2);
    }
}