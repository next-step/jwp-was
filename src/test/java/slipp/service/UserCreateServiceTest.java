package slipp.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import slipp.db.DataBase;
import slipp.exception.UserDuplicationException;
import slipp.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserCreateServiceTest {

    private final UserCreateService userCreateService = new UserCreateService();

    @DisplayName("새로운 ID로 회원가입시 DB에 회원정보 저장")
    @Test
    void addUse() {
        userCreateService.addUser(
                new User(
                        "someId",
                        "password2",
                        "name2",
                        "email2"
                )
        );

        Optional<User> someId = DataBase.findUserById("someId");
        assertThat(someId.get()).usingRecursiveComparison()
                .isEqualTo(new User(
                        "someId",
                        "password2",
                        "name2",
                        "email2"
                ));
    }

    @DisplayName("이미 가입한 ID로 회원가입시 예외 발생")
    @Test
    void addUser_duplicate_user_id() {
        DataBase.addUser(new User(
                        "someId",
                        "password",
                        "name",
                        "email"
                )
        );

        assertThatThrownBy(() -> userCreateService.addUser(
                new User(
                        "someId",
                        "password2",
                        "name2",
                        "email2"
                )
        )).isInstanceOf(UserDuplicationException.class)
                .hasMessage("이미 존재하는 회원 ID입니다. userId: someId");
    }
}