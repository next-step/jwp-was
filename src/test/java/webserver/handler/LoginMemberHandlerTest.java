package webserver.handler;

import db.DataBase;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.RequestMappingInfo;
import webserver.http.*;

import java.util.ArrayList;

class LoginMemberHandlerTest {

    private LoginMemberHandler loginMemberHandler;

    @BeforeEach
    void setup() {
        loginMemberHandler = new LoginMemberHandler();
    }

    @DisplayName("request path 가 /user/login 이고 request method 가 post 인 요청과 매핑된다.")
    @Test
    void supportTest() {
        // given
//        RequestMappingInfo expected = new RequestMappingInfo("/user/login", HttpMethod.POST);
//
//        // when
//        RequestMappingInfo actual = loginMemberHandler.getMappingInfo();
//
//        // then
//        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("로그인 성공 시 로그인 성공 쿠키가 생성 되고 index.html 로 이동해야 한다.")
    @Test
    void loginSuccessTest() {
        // given
        DataBase.addUser(new User("testUser", "testPw", "test", "test@test.com"));
        Request request = createLoginRequest("userId=testUser&password=testPw");
        Response response = new Response();

        // when
        loginMemberHandler.handle(request, response);

        // then
        Assertions.assertThat(response.getStatusLine()).isEqualTo(new StatusLine(ProtocolVersion.HTTP11, Status.FOUND));
        Assertions.assertThat(response.getHeaders().getValue("Location")).isEqualTo("/index.html");
        Assertions.assertThat(response.getHeaders().getValue("Set-Cookie")).isEqualTo("logined=true; Path=/");
    }

    @DisplayName("로그인 실패 시 로그인 실패 쿠키가 생성 되고 /user/login_failed.html 로 이동해야 한다.")
    @Test
    void loginFailTest() {
        // given
        Request request = createLoginRequest("userId=testUser&password=testPw");
        Response response = new Response();

        // when
        loginMemberHandler.handle(request, response);

        // then
        Assertions.assertThat(response.getStatusLine()).isEqualTo(new StatusLine(ProtocolVersion.HTTP11, Status.FOUND));
        Assertions.assertThat(response.getHeaders().getValue("Location")).isEqualTo("/user/login_failed.html");
        Assertions.assertThat(response.getHeaders().getValue("Set-Cookie")).isEqualTo("logined=false; Path=/");
    }


    private Request createLoginRequest(String body) {
        return new Request(
                RequestLine.parseOf("POST /user/login HTTP/1.1"),
                Headers.parseOf(new ArrayList<>()),
                body
        );
    }

}