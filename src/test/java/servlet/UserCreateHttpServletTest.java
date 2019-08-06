package servlet;

import db.DataBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Request;
import webserver.Response;
import webserver.request.RequestTest;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static webserver.response.HttpStatus.REDIRECT;

class UserCreateHttpServletTest {

    private Request request;
    private final UserCreateServlet userCreateServlet = new UserCreateServlet();

    @BeforeEach
    void setUp() throws IOException {
        request = RequestTest.getRequest("Request_CreateUser.txt");
    }

    @DisplayName("회원가입 uri 맞는지 확인")
    @Test
    void isMapping_success() {
        // when
        boolean mappingResult = userCreateServlet.isMapping(request);

        // then
        assertThat(mappingResult).isTrue();
    }

    @DisplayName("회원가입에 성공 후 main 페이지로 이동")
    @Test
    void service_success() {
        // given
        String userId = "javajigi";

        // when
        Response service = userCreateServlet.service(request);

        // then
        assertThat(service.getStatus()).isEqualTo(REDIRECT);
        assertThat(DataBase.findUserById(userId)).isNotNull();
    }
}