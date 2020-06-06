package model;

import http.request.QueryString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("user에 파라미터들을 맵핑해 저장한다.")
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
