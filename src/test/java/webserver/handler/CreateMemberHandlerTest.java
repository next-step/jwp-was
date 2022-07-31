package webserver.handler;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.RequestMappingInfo;
import webserver.http.*;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


class CreateMemberHandlerTest {

    private CreateMemberHandler createMemberHandler;

    @BeforeEach
    void setup() {
        createMemberHandler = new CreateMemberHandler();
        DataBase.clear();
    }

    @DisplayName("request path 가 /user/create 이고 request method 가 post 인 요청과 매핑된다.")
    @Test
    void supportTest() {
        // given
//        RequestMappingInfo expected = new RequestMappingInfo("/user/create", HttpMethod.POST);
//
//        // when
//        RequestMappingInfo actual = createMemberHandler.getMappingInfo();
//
//        // then
//        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("회원가입 후 index.html 로 리다이렉트 될 수 있도록 응답해야 한다.")
    @Test
    void handleTest() {
        // given
        Request request = createUserCreateRequest("userId", "passwrod", "name", "email");
        Response response = new Response();

        // when
        createMemberHandler.handle(request, response);

        // then
        assertThat(response.getStatusLine()).isEqualTo(new StatusLine(ProtocolVersion.HTTP11, Status.FOUND));
        assertThat(response.getHeaders().getValue("Location")).isEqualTo( "/index.html");
    }

    @DisplayName("회원가입 성공시 User 가 생성되어 저장되야 한다. ")
    @Test
    void createMemberTest() {
        // given
        Request request = createUserCreateRequest("userId", "password", "name", "email");
        Response response = new Response();

        // when
        createMemberHandler.handle(request, response);

        // then
        User user = DataBase.findUserById("userId");
        assertThat(user).isNotNull();
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getEmail()).isEqualTo("email");
    }

    private Request createUserCreateRequest(String userId, String password, String name, String email) {
        return new Request(
                RequestLine.parseOf("POST /user/create HTTP/1.1"),
                Headers.parseOf(new ArrayList<>()),
                String.format("userId=%s&password=%s&name=%s&email=%s", userId, password, name, email));
    }

}
