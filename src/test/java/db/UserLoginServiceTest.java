package db;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FailedLoginException;
import service.UserLoginService;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserLoginServiceTest {

    private UserLoginService userLoginService;

    @BeforeEach
    void setUp() {
        DataBase.clear();
        userLoginService = new UserLoginService();
    }

    @Test
    void login() throws FailedLoginException {
        //given
        DataBase.addUser(new User("changgunyee", "password", "name", "email"));

        //when
        userLoginService.login("changgunyee", "password");

        //then
        User changgunyee = DataBase.findUserById("changgunyee");
        assertAll(
                () -> assertEquals(changgunyee.getUserId(), "changgunyee"),
                () -> assertEquals(changgunyee.getPassword(), "password"),
                () -> assertEquals(changgunyee.getName(), "name"),
                () -> assertEquals(changgunyee.getEmail(), "email")
        );
    }

    @Test
    void login_session() throws FailedLoginException {
        //given
        DataBase.addUser(new User("changgunyee", "password", "name", "email"));
        String sessionId = userLoginService.login("changgunyee", "password");

        //when
        userLoginService.login(sessionId);

        //then
        User changgunyee = DataBase.findUserById("changgunyee");
        assertAll(
                () -> assertEquals(changgunyee.getUserId(), "changgunyee"),
                () -> assertEquals(changgunyee.getPassword(), "password"),
                () -> assertEquals(changgunyee.getName(), "name"),
                () -> assertEquals(changgunyee.getEmail(), "email")
        );
    }


    @Test
    void login_FailedLoginExceptionForLoginId() {
        assertThatThrownBy(() ->
                userLoginService.login("changgunyee", "password"))
                .isInstanceOf(FailedLoginException.class)
                .hasMessage("Failed To Login. Check changgunyee");
    }

    @Test
    void login_FailedLoginExceptionForUnknownSessionId() {
        assertThatThrownBy(() ->
                userLoginService.login("uknownSessionId"))
                .isInstanceOf(FailedLoginException.class)
                .hasMessage("Failed To Login. Check uknownSessionId");
    }


    @Test
    void login_FailedLoginExceptionForPassword() {
        DataBase.addUser(new User("changgunyee", "password", "name", "email"));

        assertThatThrownBy(() ->
                userLoginService.login("changgunyee", "wrong password"))
                .isInstanceOf(FailedLoginException.class)
                .hasMessage("Failed To Login. Check wrong password");
    }
}