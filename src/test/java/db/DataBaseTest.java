package db;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("인메모리 디비 테스트")
class DataBaseTest {

    @BeforeEach
    void init() {
        DataBase.deleteAll();
        DataBase.addUser(new User("test", "123", "test", "test@test.com"));
    }

    @Test
    @DisplayName("유저 추가 테스트")
    void addUser() {
        assertThat(DataBase.findAll()).hasSize(1);

        DataBase.addUser(new User("test2", "123", "test2", "test2@test.com"));

        assertThat(DataBase.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("유저 찾기 테스트")
    void findUser() {
        assertThat(DataBase.findUserById("test")).isNotNull();
    }

    @Test
    @DisplayName("모든 유저 찾기 테스트")
    void findAllUser() {
        assertThat(DataBase.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("메모리 비우기 테스트")
    void clear() {
        DataBase.deleteAll();
        assertThat(DataBase.findAll()).hasSize(0);
    }
}