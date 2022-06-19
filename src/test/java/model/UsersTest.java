package model;

import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.Fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UsersTest {

    @DisplayName("Collection 으로부터 Users 일급 컬렉션을 만들고, 이를 다시 List 로 변환할 수 있다.")
    @Test
    void from() {
        List<User> userList = Fixture.createUserList();
        Collection<User> collection = userList;
        Users users = Users.from(collection);
        assertThat(users.toList())
                .isEqualTo(userList);
    }

    @DisplayName("Users 를 toList 로 변환할 때는 방어적 복사가 적용된다.")
    @Test
    void toList() {
        Users users = Users.from(Fixture.createUserList());

        String userId = "javajigi";
        String password = "P@ssw0rD";
        String name = "JaeSung";
        String email = "javajigi@slipp.net";
        User user = new User(userId, password, name, email);

        assertThatThrownBy(
                () -> users.toList().add(user)
        ).isInstanceOf(UnsupportedOperationException.class);
    }
}
