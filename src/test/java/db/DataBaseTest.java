package db;

import exception.InvalidUserException;
import exception.NotFoundUserException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("DataBase 기능 테스트")
class DataBaseTest {

    @BeforeEach
    void setUp() {
        DataBase.deleteAll();
    }

    @Test
    @DisplayName("사용자를 추가한다.")
    void addUser() {
        User user = new User("javajigi", "password", "JaeSung", "javajigi@slipp.net");

        DataBase.addUser(user);

        assertThat(DataBase.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("사용자 Id가 이미 존재할 경우 InvalidUserException이 발생한다.")
    void existUserId() {
        User existUser = new User("javajigi", "password", "JaeSung", "javajigi@slipp.net");
        DataBase.addUser(existUser);

        User newUser = new User("javajigi", "password", "HanGyeol", "limhangyeol@edu.nextstep.camp");

        assertThatThrownBy(() -> DataBase.addUser(newUser))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("invalid user id: (javajigi)");
    }

    @Test
    @DisplayName("userId로 존재하는 사용자를 조회한다.")
    void findUserById() {
        User user = new User("javajigi", "password", "JaeSung", "javajigi@slipp.net");
        DataBase.addUser(user);

        User actual = DataBase.findUserById(user.getUserId())
                .orElseThrow(() -> new NotFoundUserException(user.getUserId()));

        assertThat(actual.getUserId()).isEqualTo(user.getUserId());
        assertThat(actual.getEmail()).isEqualTo(user.getEmail());
        assertThat(actual.getName()).isEqualTo(user.getName());
    }

    @Test
    @DisplayName("존재하지 않는 사용자 Id로 조회할 경우 NotFoundUserException이 발생한다.")
    void findUserByIdFailedWhenNonExistUser() {
        String userId = "javajigi";

        assertThatThrownBy(() -> DataBase.findUserById(userId)
                        .orElseThrow(() -> new NotFoundUserException(userId))
        )
                .isInstanceOf(NotFoundUserException.class)
                .hasMessage("not found user id: (javajigi)");
    }
}
