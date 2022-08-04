package webserver.http.response.handler.post;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;
import webserver.http.request.header.RequestHeader;

class UserLoginResponseHandlerTest {

    @BeforeEach
    void setup() {
        DataBase.clear();
    }

    @DisplayName("로그인 유저 응답")
    @Test
    void loginUser() {
        // given
        UserLoginResponseHandler handler = new UserLoginResponseHandler();
        DataBase.addUser(
                new User(
                        "javajigi",
                        "password",
                        "%EB%B0%95%EC%9E%AC%EC%84%B1",
                        "javajigi%40slipp.net"
                )
        );

        // when
        String actual = handler.run(
                RequestHeader.create("POST /user/login HTTP/1.1"),
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net",
                "".getBytes()
        );

        String sessionId = getSessionId(actual);

        // then
        assertThat(actual).isEqualTo(
                "HTTP/1.1 302 Found\r\n" +
                        "Set-Cookie: sessionId=" + sessionId + " Path=/\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Location: /index.html\r\n" +
                        "\r\n"
        );
    }

    @DisplayName("비로그인 유저 응답")
    @Test
    void loginNotUser() {
        // given
        UserLoginResponseHandler handler = new UserLoginResponseHandler();

        // when
        String actual = handler.run(
                RequestHeader.create("POST /user/login HTTP/1.1"),
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net",
                "".getBytes()
        );

        String sessionId = getSessionId(actual);

        // then
        assertThat(actual).isEqualTo(
                "HTTP/1.1 302 Found\r\n" +
                        "Set-Cookie: sessionId=" + sessionId + " Path=/\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Location: /user/login_failed.html\r\n" +
                        "\r\n"
        );
    }

    private String getSessionId(String actual) {
        return actual.split("\r\n")[1].split(" ")[1].split("=")[1];
    }
}
