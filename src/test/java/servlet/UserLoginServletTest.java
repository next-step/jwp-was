package servlet;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Request;
import webserver.RequestTest;
import webserver.Response;
import webserver.request.Cookie;

import java.io.IOException;

import static com.google.common.net.HttpHeaders.SET_COOKIE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static servlet.UserLoginServlet.COOKIE_OF_LOGIN;
import static webserver.HttpStatus.REDIRECT;

class UserLoginServletTest {

    private final UserLoginServlet userLoginServlet = new UserLoginServlet();

    @DisplayName("로그인 uri 맞는지 확인")
    @Test
    void isMapping_success() throws IOException {
        // given
        Request request = RequestTest.requestOfLogin();

        // when
        boolean mappingResult = userLoginServlet.isMapping(request);

        // then
        assertThat(mappingResult).isTrue();
    }

    @DisplayName("로그인 성공")
    @Test
    void service_success() throws IOException {
        // given
        String userId = "javajigi";
        Request request = RequestTest.requestOfLogin();

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
    void service_fail() throws IOException {
        // given
        String userId = "javajigi";
        Request request = RequestTest.requestOfLogin();

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
        String cookieOfLogin = response.getHeaderOf(SET_COOKIE);

        String checkedLogin = cookieOfLogin.split(";")[0]
                                       .split("=")[1];
        return Boolean.valueOf(checkedLogin);
    }
}