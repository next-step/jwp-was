package model;

import http.QueryString;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void createUser() {
        QueryString queryString = new QueryString("userId=javajigi&password=password&name=JaeSung&email=test@com");
        User user = new User(queryString);

        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("JaeSung");
        assertThat(user.getEmail()).isEqualTo("test@com");
    }
}
