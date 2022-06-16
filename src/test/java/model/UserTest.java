package model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void from() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("userId", "changgunyee");
        queryMap.put("password", "password");
        queryMap.put("name", "name");
        queryMap.put("email", "email");

        User changgunyee = User.from(queryMap);
        assertAll(
                () -> assertEquals(changgunyee.getUserId(), "changgunyee"),
                () -> assertEquals(changgunyee.getPassword(), "password"),
                () -> assertEquals(changgunyee.getName(), "name"),
                () -> assertEquals(changgunyee.getEmail(), "email")
        );
    }

    @Test
    void from_IllegalUserInputException() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("userId", "changgunyee");
        queryMap.put("name", "name");
        queryMap.put("email", "email");

        assertThatThrownBy(() -> User.from(queryMap))
                .isInstanceOf(IllegalUserInputException.class)
                .hasMessage("Wrong Input To Make User. field: password, input: null");

    }
}