package model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LoginRequestTest {

    @DisplayName("userId 값은 빈 값이 될 수 없다.")
    @Test
    void createFailedByUserIdEmpty() {
        assertThatThrownBy(() -> new LoginRequest(
                "",
                "password"
        ));
    }

    @DisplayName("password 값은 빈 값이 될 수 없다.")
    @Test
    void createFailedByPasswordEmpty() {
        assertThatThrownBy(() -> new LoginRequest(
                "userId",
                ""
        ));
    }
}