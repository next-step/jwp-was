package db;

import model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DataBaseTest {
    private DataBase sut;

    @Test
    void find_user() {
        // given
        String userId = "testId";
        sut.addUser(new User("notExistUserId", "1", "1", "1"));

        // when
        User user = sut.findUserById(userId);

        // then
        assertThat(user.getUserId()).isEqualTo(userId);
    }

    @Test
    void find_user_isNotLoginedId() {
        // given
        String userId = "testId";
        sut.addUser(new User("notExistUserId", "1", "1", "1"));

        // when
        User user = sut.findUserById(userId);

        // then
        assertThat(user).isNull();
    }

    @Test
    void find_userId_isNull() {
        // given
        String userId = null;

        // when
        User user = sut.findUserById(userId);

        // then
        assertThat(user).isNull();
    }

}