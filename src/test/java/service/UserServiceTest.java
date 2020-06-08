package service;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @BeforeAll
    void initData() {
        User user = new User("test", "1234", "테스트", "test@gmail.com");

        DataBase.addUser(user);
    }

    @Test
    void login() {
        boolean success = UserService.login("test", "1234");

        assertThat(success).isEqualTo(true);
    }

}
