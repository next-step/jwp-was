package servlet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Request;
import webserver.request.RequestTest;
import webserver.Response;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static webserver.response.HttpStatus.REDIRECT;
import static webserver.response.HttpStatus.SUCCESS;

class UserListServletTest {

    private final UserListServlet userListServlet = new UserListServlet();

    @DisplayName("리스트 uri 맞는지 확인")
    @Test
    void isMapping_success() throws IOException {
        // given
        Request httpRequest = RequestTest.requestOfList();

        // when
        boolean mappingResult = userListServlet.isMapping(httpRequest);

        // then
        assertThat(mappingResult).isTrue();
    }

    @DisplayName("로그인 했을 시 회원 리스트")
    @Test
    void service_success() throws IOException {
        // given
        Request httpRequest = RequestTest.requestOfList();

        // when
        Response service = userListServlet.service(httpRequest);

        // thene
        assertThat(service.getStatus()).isEqualTo(SUCCESS);
    }

    @DisplayName("로그인 안했을 시 로그인 페이지로 이동")
    @Test
    void service_fail() throws IOException {
        // given
        Request httpRequest = RequestTest.requestOfList_notLogin();

        // when
        Response service = userListServlet.service(httpRequest);

        // then
        assertThat(service.getStatus()).isEqualTo(REDIRECT);
    }
}