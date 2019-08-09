package controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpSession;
import webserver.Request;
import webserver.Response;
import webserver.request.RequestTest;
import webserver.session.MockHttpSession;
import webserver.session.SessionContainer;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static webserver.response.HeaderProperty.LOCATION;

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
        boolean mappingResult = UserLoginController.isMapping(request);

        // then
        assertThat(mappingResult).isTrue();
    }

    @DisplayName("로그인 성공")
    @Test
    void service_success() throws Exception {
        // given
        String userId = "javajigi";
        SessionContainer.register(new MockHttpSession());
        HttpSession httpSession = request.getSession();

        // when
        DataBase.addUser(getUser(userId));
        Response response = userLoginController.service(request);
        User user = (User) httpSession.getAttribute("user");

        // then
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(response.getHeader(LOCATION)).isEqualTo("/index.html");
    }

    @DisplayName("로그인 실패")
    @Test
    void service_fail() throws Exception {
        DataBase.deleteAll();

        // when
        Response response = userLoginController.service(request);
        User user = (User) request.getSession().getAttribute("user");

        // then
        assertThat(user).isNull();
        assertThat(response.getHeader(LOCATION)).isEqualTo("/user/login_failed.html");
    }

    private User getUser(String userId) {
        return new User(userId, "password", "name", "email");
    }
}