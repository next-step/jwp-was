package application;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserServiceTest {
    @Test
    void createUser() {
        CreateUserCommand createUserCommand = new CreateUserCommand("userId", "password", "name", "email");

        CreateUserService.createUser(createUserCommand);

        User user = DataBase.findUserById("userId");

        assertThat(user.getUserId()).isEqualTo("userId");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getEmail()).isEqualTo("email");
    }
}
