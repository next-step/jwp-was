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

    @DisplayName("유저목록 조회 DB 에 저장된 모든 유저들을 반환한다.")
    @Test
    void getUserList() {
        assertThat(
                UserService.getUsers().toList()
        ).hasSameElementsAs(users);
    }

    @DisplayName("회원가입 시, DB 에 User 가 추가된다.")
    @Test
    void createUser() {
        String userId = "javajigi";
        String password = "P@ssw0rD";
        String name = "JaeSung";
        String email = "javajigi@slipp.net";
        User user = new User(userId, password, name, email);

        UserService.createUser(user);

        assertThat(
                UserService.getUsers().toList()
        ).contains(user);
    }
}
