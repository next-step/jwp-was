package controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Request;
import webserver.request.RequestTest;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static controller.UserCreateControllerTest.createResponse;

class UserLoginControllerTest {

    private Request request;
    private final UserLoginController userLoginController = new UserLoginController();

    @BeforeEach
    void setUp() throws IOException {
        request = RequestTest.getRequest("Request_Login.txt");
    }

    @DisplayName("로그인 uri 맞는지 확인")
    @Test
    void isMapping_success() {
        // when
        boolean mappingResult = userLoginController.isMapping(request);

        // then
        assertThat(mappingResult).isTrue();
    }

    @DisplayName("로그인 성공")
    @Test
    void service_success() throws Exception {
        // given
        DataBase.addUser( new User("javajigi", "password", "java", "java@email.com"));

        // when
        userLoginController.service(request, createResponse("Response_Login_Success.txt"));
    }

    @DisplayName("로그인 실패")
    @Test
    void service_fail() throws Exception {
        DataBase.deleteAll();

        // when
        userLoginController.service(request, createResponse("Response_Login_Fail.txt"));
    }
}