package db;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DataBaseTest {

    @BeforeEach
    void setUp() {
        DataBase.clear();
    }

    @Test
    void login() throws FailedLoginException {
        //given
        DataBase.addUser(new User("changgunyee", "password", "name", "email"));

        //when
        DataBase.login("changgunyee", "password");

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
                DataBase.login("changgunyee", "password"))
                .isInstanceOf(FailedLoginException.class)
                .hasMessage("Failed To Login. Check changgunyee");
    }

    @Test
    void login_FailedLoginExceptionForPassword() {
        DataBase.addUser(new User("changgunyee", "password", "name", "email"));

        assertThatThrownBy(() ->
                DataBase.login("changgunyee", "wrong password"))
                .isInstanceOf(FailedLoginException.class)
                .hasMessage("Failed To Login. Check wrong password");
    }
}