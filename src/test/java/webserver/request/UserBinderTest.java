package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserBinderTest {

    @DisplayName("파라미터로 전달된 문자열을 이용해서 User 객체를 생성한다")
    @Test
    void create_user() {
        Map<String, String> parameters = Map.of(
            "userId", "admin",
            "password", "pass",
            "name", "관리자",
            "email", "admin@admin.com"
        );

        User user = UserBinder.from(parameters);

        assertThat(user).isEqualTo(new User("admin", "pass", "관리자", "admin@admin.com"));
    }

    @DisplayName("User의 필드와 일치하는 파라미터가 없는 경우 null 값을 할당한다")
    @Test
    void create_user_with_null_values() {
        Map<String, String> parameters = Map.of(
            "userId", "admin",
            "password", "pass"
        );

        User user = UserBinder.from(parameters);

        assertThat(user).isEqualTo(new User("admin", "pass", null, null));
    }
}
