package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Request;
import webserver.Response;
import webserver.request.RequestTest;
import webserver.response.HeaderProperty;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserCreateControllerTest {

    private Request request;
    private final UserCreateController userCreateController = new UserCreateController();

    @BeforeEach
    void setUp() throws IOException {
        request = RequestTest.getRequest("Request_CreateUser.txt");
    }

    @DisplayName("회원가입 uri 맞는지 확인")
    @Test
    void isMapping_success() {
        // when
        boolean mappingResult = userCreateController.isMapping(request);

        // then
        assertThat(mappingResult).isTrue();
    }

    @DisplayName("회원가입에 성공 후 main 페이지로 이동")
    @Test
    void service_success() throws Exception {
        // when
        Response response = userCreateController.service(request);
        String location = response.getHeader(HeaderProperty.LOCATION);

        // then
        assertThat(location).isEqualTo("/index.html");
    }
}