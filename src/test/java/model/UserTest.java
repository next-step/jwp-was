package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("User가 특정 비밀번호를 가지고 있는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"password, true", "otherPassword, false"})
    void hasPassword(String password, boolean expected) {
        User user = new User("userId",
                "password",
                "name",
                "email@email"
        );

        boolean actual = user.hasPassword(password);

        assertThat(actual).isEqualTo(expected);
    }
}