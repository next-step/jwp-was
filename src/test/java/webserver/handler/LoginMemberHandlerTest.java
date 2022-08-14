package webserver.handler;

import db.DataBase;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.ModelAndView;
import webserver.http.*;

import java.util.ArrayList;

class LoginMemberHandlerTest {

    @BeforeEach
    void setup() {
        DataBase.clear();
    }

    @DisplayName("로그인 성공 시 세션에 로그인 성공이라고 저장하고 index.html 로 이동해야 한다.")
    @Test
    void loginSuccessTest() {
        // given
        DataBase.addUser(new User("testUser", "testPw", "test", "test@test.com"));
        HttpSession httpSession = new HttpSession("TEST");
        LoginMemberHandler loginMemberHandler = new LoginMemberHandler((httpRequest, httpResponse) -> httpSession);

        HttpRequest httpRequest = createLoginRequest("userId=testUser&password=testPw");
        HttpResponse httpResponse = new HttpResponse();

        // when
        ModelAndView modelAndView = loginMemberHandler.handle(httpRequest, httpResponse);

        // then
        Assertions.assertThat(modelAndView.getView()).isEqualTo("redirect:/index.html");
        Assertions.assertThat(httpSession.getAttribute("logined")).isEqualTo(true);
    }

    @DisplayName("로그인 실패 시 세션에 로그인 실패라고 저장하고 /user/login_failed.html 로 이동해야 한다.")
    @Test
    void loginFailTest() {
        // given
        HttpSession httpSession = new HttpSession("TEST");
        LoginMemberHandler loginMemberHandler = new LoginMemberHandler((httpRequest, httpResponse) -> httpSession);
        HttpRequest httpRequest = createLoginRequest("userId=testUser&password=testPw");
        HttpResponse httpResponse = new HttpResponse();

        // when
        ModelAndView modelAndView = loginMemberHandler.handle(httpRequest, httpResponse);

        // then
        Assertions.assertThat(modelAndView.getView()).isEqualTo("redirect:/user/login_failed.html");
        Assertions.assertThat(httpSession.getAttribute("logined")).isEqualTo(false);
    }


    private HttpRequest createLoginRequest(String body) {
        return new HttpRequest(
                RequestLine.parseOf("POST /user/login HTTP/1.1"),
                Headers.parseOf(new ArrayList<>()),
                body
        );
    }

}
