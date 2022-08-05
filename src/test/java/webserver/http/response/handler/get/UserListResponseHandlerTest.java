package webserver.http.response.handler.get;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.header.RequestHeader;
import webserver.http.response.handler.get.UserListResponseHandler;
import webserver.http.session.HttpSession;
import webserver.http.session.HttpSessionStorage;

class UserListResponseHandlerTest {

    @DisplayName("로그인 되어 있을 때 유저의 리스트가 보여야 한다.")
    @Test
    void loginUserList() {
        // given
        HttpSession httpSession = new HttpSession(UUID.randomUUID());
        httpSession.setLogin(true);

        HttpSessionStorage.setSession(httpSession.getId(), httpSession);

        UserListResponseHandler handler = new UserListResponseHandler();

        // when
        String actual = handler.run(
                RequestHeader.create(
                        "GET /user/list HTTP/1.1\n" +
                                "Host: localhost:8080\n" +
                                "Connection: keep-alive\n" +
                                "Accept: */*\n" +
                                "Cookie: sessionId=" + httpSession.getId()
                ),
                "",
                "".getBytes()
        ).toString();

        // then
        assertThat(actual).isEqualTo(
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 0\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "\r\n"
        );
    }

    @DisplayName("로그인 되어 있지 않은 경우 로그인 화면으로 이동한다.")
    @Test
    void notLoginUserList() {
        // given
        UserListResponseHandler handler = new UserListResponseHandler();

        // when
        String actual = handler.run(
                RequestHeader.create(
                        "GET /user/list HTTP/1.1\n" +
                                "Host: localhost:8080\n" +
                                "Connection: keep-alive\n" +
                                "Accept: */*\n"
                ),
                "",
                "".getBytes()
        ).toString();

        // then
        assertThat(actual).isEqualTo(
                "HTTP/1.1 302 Found\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Location: /user/login_failed.html\r\n" +
                        "\r\n"
        );
    }
}
