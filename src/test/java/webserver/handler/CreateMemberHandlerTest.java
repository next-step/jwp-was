package webserver.handler;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.ModelAndView;
import webserver.http.Headers;
import webserver.http.Request;
import webserver.http.RequestLine;
import webserver.http.Response;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


class CreateMemberHandlerTest {

    private CreateMemberHandler createMemberHandler;

    @BeforeEach
    void setup() {
        createMemberHandler = new CreateMemberHandler();
        DataBase.clear();
    }

    @DisplayName("회원가입 후 index.html 로 리다이렉트 될 수 있도록 응답해야 한다.")
    @Test
    void handleTest() {
        // given
        Request request = createUserCreateRequest("userId", "passwrod", "name", "email");
        Response response = new Response();

        // when
        ModelAndView modelAndView = createMemberHandler.handle(request, response);

        // then
        assertThat(modelAndView.getView()).isEqualTo("redirect:/index.html");
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
