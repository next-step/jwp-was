package service;

import db.DataBase;
import java.util.List;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private List<User> users;

    @BeforeEach
    void setUp() {
        DataBase.clear();
        users = Fixture.createUserList();
        users.forEach(DataBase::addUser);
    }

    @DisplayName("유저목록 조회시, DB 에 저장된 모든 유저들을 반환한다.")
    @Test
    void findAll() {
        assertThat(
                UserService.findAll().toList()
        ).hasSameElementsAs(users);
    }

    @DisplayName("회원가입 시, DB 에 User 가 추가된다.")
    @Test
    void save() {
        String userId = "javajigi";
        String password = "P@ssw0rD";
        String name = "JaeSung";
        String email = "javajigi@slipp.net";
        User user = new User(userId, password, name, email);

        UserService.save(user);

        assertThat(
                UserService.findAll().toList()
        ).contains(user);
    }
}
