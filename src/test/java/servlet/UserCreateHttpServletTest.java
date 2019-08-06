package servlet;

import db.DataBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Request;
import webserver.Response;
import webserver.request.RequestTest;

import java.io.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static webserver.response.HttpStatus.REDIRECT;

class UserCreateHttpServletTest {

    private final UserCreateServlet userCreateServlet = new UserCreateServlet();

    @DisplayName("회원가입 uri 맞는지 확인")
    @Test
    void isMapping_success() throws IOException {
        // given
        Request httpRequest = RequestTest.requestOfCreateUser();

        // when
        boolean mappingResult = userCreateServlet.isMapping(httpRequest);

        // then
        assertThat(mappingResult).isTrue();
    }

    @DisplayName("회원가입에 성공 후 main 페이지로 이동")
    @Test
    void service_success() throws IOException {
        // given
        String userId = "javajigi";
        Request httpRequest = RequestTest.requestOfCreateUser();

        // when
        Response service = userCreateServlet.service(httpRequest);

        // then
        assertThat(service.getStatus()).isEqualTo(REDIRECT);
        assertThat(DataBase.findUserById(userId)).isNotNull();
    }
}