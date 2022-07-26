package webserver.user;

import model.User;
import org.junit.jupiter.api.Test;
import webserver.http.Path;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserFactoryTest {

    @Test
    void create() {
        Map<String, String> map = Map.of(
                "userId", "foo",
                "password", "pass",
                "name", "foo",
                "email", "test@test.com"
        );
        final Path path = Path.createWithGetMethod("/user/create?userId=foo&password=pass&name=foo&email=test@test.com");

        User actual = UserFactory.from(path);

        assertThat(actual.getUserId()).isEqualTo("foo");
        assertThat(actual.getPassword()).isEqualTo("pass");
        assertThat(actual.getName()).isEqualTo("foo");
        assertThat(actual.getEmail()).isEqualTo("test@test.com");
    }
}
