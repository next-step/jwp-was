package webserver.service;

import db.DataBase;
import exception.BadRequestException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HelpData;
import webserver.request.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService userService;
    HttpRequest request;

    @BeforeEach
    void setup() throws IOException {
        DataBase.clear();
        BufferedReader br = HelpData.postHelpData();
        request = HttpRequest.parsing(br);
        userService = new UserService(request);

        DataBase.addUser(new User("java", "password", "name", "java@java.com"));
    }

    @Test
    @DisplayName("요청에 대하여 유저를 저장")
    void save_member() {
        String savedUserId = userService.saveMember();

        User findUser = userService.findByUserId(savedUserId);

        assertThat(savedUserId).isEqualTo("javajigi");
        assertThat(findUser.getPassword()).isEqualTo("password");
        assertThat(findUser.getEmail()).isEqualTo("javajigi%40slipp.net");
        assertThat(findUser.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
    }

    @Test
    void find_member() {
        assertThat(userService.findByUserId("java").getEmail()).isEqualTo("java@java.com");
    }

    @Test
    @DisplayName("존재하는 유저 저장 시 예외")
    void find_user_not_exist_exception() {
        // 저장 후
        userService.saveMember();

        // 같은 user 재 저장 시 에러
        assertThatThrownBy(() -> userService.saveMember())
                .isInstanceOf(BadRequestException.class);
    }
}