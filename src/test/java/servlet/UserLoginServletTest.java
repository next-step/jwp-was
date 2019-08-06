package servlet;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Request;
import webserver.Response;
import webserver.request.RequestTest;
import webserver.response.HeaderProperty;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static webserver.response.HttpStatus.REDIRECT;

class UserLoginServletTest {

    private Request request;
    private final UserLoginServlet userLoginServlet = new UserLoginServlet();

    @BeforeEach
    void setUp() throws IOException {
        request = RequestTest.getRequest("Request_Login.txt");
    }

    @DisplayName("로그인 uri 맞는지 확인")
    @Test
    void isMapping_success() {
        // when
        boolean mappingResult = userLoginServlet.isMapping(request);

        // then
        assertThat(mappingResult).isTrue();
    }

    @DisplayName("로그인 성공")
    @Test
    void service_success() {
        // given
        String userId = "javajigi";

        // when
        DataBase.addUser(getUser(userId));
        Response service = userLoginServlet.service(request);
        Boolean checkedLogin = getCheckedLogin(service);

        // then
        assertThat(checkedLogin).isTrue();
        assertThat(service.getStatus()).isEqualTo(REDIRECT);
    }

    @DisplayName("로그인 실패")
    @Test
    void service_fail() {
        // given
        String userId = "noUser";

        // when
        Response service = userLoginServlet.service(request);

        // then
        assertThat(DataBase.findUserById(userId)).isNull();
        assertThat(service.getStatus()).isEqualTo(REDIRECT);
    }

    private User getUser(String userId) {
        return new User(userId, "password", "name", "email");
    }

    private Boolean getCheckedLogin(Response response) {
        String cookieOfLogin = response.getHeader(HeaderProperty.SET_COOKIE);

        String checkedLogin = cookieOfLogin.split(";")[0]
                                       .split("=")[1];
        return Boolean.valueOf(checkedLogin);
    }
}