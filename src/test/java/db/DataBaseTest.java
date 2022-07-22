package db;

import static org.assertj.core.api.Assertions.assertThat;

import model.User;
import model.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataBaseTest {

    @BeforeEach
    void setUp() {
        DataBase.deleteAll();
    }

    @Test
    void addUserTest() {
        assertThat(DataBase.findAll()).isEmpty();

        DataBase.addUser(UserTest.TEST_USER);

        assertThat(DataBase.findAll()).hasSize(1);
    }

    @Test
    void findUserByIdTest() {
        DataBase.addUser(UserTest.TEST_USER);

        User savedUser = DataBase.findUserById(UserTest.TEST_USER.getUserId());

        assertThat(savedUser).isEqualTo(UserTest.TEST_USER);
    }

}
