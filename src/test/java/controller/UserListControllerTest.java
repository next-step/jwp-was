package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Request;
import webserver.request.RequestTest;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static controller.UserCreateControllerTest.createResponse;

class UserListControllerTest {

    private Request request;
    private final UserListController userListController = new UserListController();

    @DisplayName("리스트 uri 맞는지 확인")
    @Test
    void isMapping_success() throws IOException {
        // given
        request = RequestTest.getRequest("Request_List.txt");

        // when
        boolean mappingResult = UserListController.isMapping(request);

        // then
        assertThat(mappingResult).isTrue();
    }

    @DisplayName("로그인 했을 시 회원 리스트")
    @Test
    void service_success() throws Exception {
        // given
        request = RequestTest.getRequest("Request_List.txt");

        // when
        userListController.service(request, createResponse("Response_List.txt"));
    }

    @DisplayName("로그인 안했을 시 로그인 페이지로 이동")
    @Test
    void service_fail() throws Exception {
        // given
        request = RequestTest.getRequest("Request_List_Fail.txt");

        // when
        userListController.service(request, createResponse("Response_List_No_Login.txt"));
    }
}