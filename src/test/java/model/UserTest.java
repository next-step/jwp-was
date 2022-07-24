package model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @DisplayName("User의 정보에 빈 값이 들어갈 경우 생성에 실패되어야 한다.")
    @Test
    void createFailedByEmptyValue() {
        assertThatThrownBy(() -> new User(
                "userId",
                "",
                "name",
                "email@email.com"))
                .isInstanceOf(UserException.class)
                .hasMessage("password가 빈 값 입니다.");
    }
}
