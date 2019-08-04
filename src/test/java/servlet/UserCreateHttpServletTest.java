package servlet;

import db.DataBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpStatus;
import webserver.Request;
import webserver.RequestTest;
import webserver.Response;

import java.io.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static webserver.HttpStatus.REDIRECT;

class UserCreateHttpServletTest {

    private final UserCreateServlet userCreateServlet = new UserCreateServlet();

    @DisplayName("회원가입 uri 맞는지 확인")
    @Test
    void isMapping_success() throws IOException {
        // given
        Request request = RequestTest.requestOfCreateUser();

        // when
        boolean mappingResult = userCreateServlet.isMapping(request);

        // then
        assertThat(mappingResult).isTrue();
    }

    @DisplayName("회원가입에 성공 후 main 페이지로 이동")
    @Test
    void service_success() throws IOException {
        // given
        String userId = "javajigi";
        Request request = RequestTest.requestOfCreateUser();

        // when
        Response service = userCreateServlet.service(request);

        // then
        assertThat(service.getStatus()).isEqualTo(REDIRECT);
        assertThat(DataBase.findUserById(userId)).isNotNull();
    }
}