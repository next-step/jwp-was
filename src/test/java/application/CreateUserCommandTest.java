package application;

import model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateUserCommandTest {

    @Test
    void create_exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CreateUserCommand("", "password", "name", "email");
        });
    }

    @Test
    void toUser() {
        User user = new CreateUserCommand("userId", "password", "name", "email").toUser();

        assertThat(user.getUserId()).isEqualTo("userId");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getEmail()).isEqualTo("email");
    }
}
