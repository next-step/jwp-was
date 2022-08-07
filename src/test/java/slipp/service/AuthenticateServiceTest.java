package slipp.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import slipp.db.DataBase;
import slipp.exception.AuthenticationFailException;
import slipp.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthenticateServiceTest {
    private final AuthenticateService authenticateService = new AuthenticateService();

    @BeforeEach
    void setUp() {
        DataBase.addUser(
                new User(
                        "user",
                        "password",
                        "name",
                        "email@com"
                )
        );
    }

    @AfterEach
    void tearDown() {
        DataBase.clearAll();
    }

    @DisplayName("userId와 password가 일치한 회원 정보가 존재하면 해당 회원의 정보를 반환")
    @Test
    void authenticate() {
        User actual = authenticateService.authenticate("user", "password");

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(
                        new User(
                                "user",
                                "password",
                                "name",
                                "email@com"
                        )
                );
    }

    @DisplayName("userId와 password가 모두 일치하는 회원 정보가 존재하지 않으면 예외 발생")
    @ParameterizedTest
    @CsvSource(value = {"noUser, password", "noUser, noPassword", "user, noPassword"})
    void authenticateFail(String userId, String password) {
        assertThatThrownBy(() -> authenticateService.authenticate(userId, password))
                .isInstanceOf(AuthenticationFailException.class)
                .hasMessage("입력하신 정보와 일치하는 회원정보가 존재하지 않습니다.");
    }
}