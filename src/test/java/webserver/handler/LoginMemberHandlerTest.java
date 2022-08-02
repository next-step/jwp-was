package webserver.handler;

import db.DataBase;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.ModelAndView;
import webserver.http.Headers;
import webserver.http.HttpRequest;
import webserver.http.RequestLine;
import webserver.http.Response;

import java.util.ArrayList;

class LoginMemberHandlerTest {

    private LoginMemberHandler loginMemberHandler;

    @BeforeEach
    void setup() {
        loginMemberHandler = new LoginMemberHandler();
        DataBase.clear();
    }

    @DisplayName("로그인 성공 시 로그인 성공 쿠키가 생성 되고 index.html 로 이동해야 한다.")
    @Test
    void loginSuccessTest() {
        // given
        DataBase.addUser(new User("testUser", "testPw", "test", "test@test.com"));
        HttpRequest httpRequest = createLoginRequest("userId=testUser&password=testPw");
        Response response = new Response();

        // when
        ModelAndView modelAndView = loginMemberHandler.handle(httpRequest, response);

        // then
        Assertions.assertThat(modelAndView.getView()).isEqualTo("redirect:/index.html");
        Assertions.assertThat(response.getHeaders().getValue("Set-Cookie")).isEqualTo("logined=true; Path=/");
    }

    @DisplayName("로그인 실패 시 로그인 실패 쿠키가 생성 되고 /user/login_failed.html 로 이동해야 한다.")
    @Test
    void loginFailTest() {
        // given
        HttpRequest httpRequest = createLoginRequest("userId=testUser&password=testPw");
        Response response = new Response();

        // when
        ModelAndView modelAndView = loginMemberHandler.handle(httpRequest, response);

        // then
        Assertions.assertThat(modelAndView.getView()).isEqualTo("redirect:/user/login_failed.html");
        Assertions.assertThat(response.getHeaders().getValue("Set-Cookie")).isEqualTo("logined=false; Path=/");
    }


    private HttpRequest createLoginRequest(String body) {
        return new HttpRequest(
                RequestLine.parseOf("POST /user/login HTTP/1.1"),
                Headers.parseOf(new ArrayList<>()),
                body
        );
    }

}
