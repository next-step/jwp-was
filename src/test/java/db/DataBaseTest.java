package db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import model.User;
import model.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataBaseTest {

    @BeforeEach
    void setUp() {
        DataBase.deleteAll();
    }

    @DisplayName("유저아이디가 중복되는 유저를 저장하면 실패한다.")
    @Test
    void addUserDuplicatedTest() {
        assertThat(DataBase.findAll()).isEmpty();

        DataBase.addUser(UserTest.TEST_USER);

        assertThatThrownBy(
            () -> DataBase.addUser(UserTest.TEST_USER)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("유저 저장에 성공한다.")
    @Test
    void addUserTest() {
        assertThat(DataBase.findAll()).isEmpty();

        DataBase.addUser(UserTest.TEST_USER);

        assertThat(DataBase.findAll()).hasSize(1);
    }

    @DisplayName("유저를 아이디로 찾는다.")
    @Test
    void findUserByIdTest() {
        DataBase.addUser(UserTest.TEST_USER);

        User savedUser = DataBase.findUserById(UserTest.TEST_USER.getUserId()).get();

        assertThat(savedUser).isEqualTo(UserTest.TEST_USER);
    }

}
