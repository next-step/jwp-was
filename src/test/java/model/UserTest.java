package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.request.header.HttpHeader;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    @DisplayName("User 객체를 생성한다.")
    void create_User() {
        User user = new User("javajigi", "password", "JaeSung", "javajigi@slipp.net");
        assertThat(user).isNotNull().isInstanceOf(User.class);
    }

    @ParameterizedTest
    @DisplayName("User Password 일치하는지 판단한다")
    @CsvSource(value = {
            "password, true",
            "passwords, false"
    })
    void create_User(String password, boolean trueOrFalse) {
        User user = new User("javajigi", "password", "JaeSung", "javajigi@slipp.net");
        assertThat(user.isPasswordCorrect(password)).isEqualTo(trueOrFalse);
    }
}