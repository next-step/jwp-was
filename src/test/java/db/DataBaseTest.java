package db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DataBaseTest {

    User nextstep;
    User 자바지기;

    @BeforeEach
    void setup() {
        자바지기 = new User("자바지기", "1111", "박재성", "java@nextstep.com");
        nextstep = new User("nextstep", "1234", "넥스트스텝", "nextstep@nextstep.com");
    }

    @Test
    @DisplayName("userId 기준으로 user 조회하기")
    void findUserByIdTest() {
        User user = DataBase.findUserById("nextstep");
        User 비교값 = new User("nextstep", "1234", "넥스트스텝", "nextstep@nextstep.com");

        assertThat(user.equals(비교값));
    }

    @Test
    @DisplayName("유저를 추가해 보는 테스트를 합니다.")
    void addUserTest() {
        User 추가할_값 = 자바지기;
        DataBase.addUser(추가할_값);

        User user = DataBase.findUserById("자바지기");
        assertThat(user).isEqualTo(추가할_값);
    }

    @Test
    @DisplayName("현재 유저 전체를 조회합니다.")
    void findAllTest() throws Exception {
        User 추가할_값 = 자바지기;
        DataBase.addUser(추가할_값);

        Collection<User> userList = DataBase.findAll();

        assertAll(() -> {
            assertThat(userList).hasSize(2);
            assertThat(userList).contains( 자바지기, nextstep );
        });

    }

}
