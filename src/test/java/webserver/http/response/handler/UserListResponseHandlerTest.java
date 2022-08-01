package webserver.http.response.handler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.header.RequestHeader;

class UserListResponseHandlerTest {

    @DisplayName("로그인 되어 있을 때 유저의 리스트가 보여야 한다.")
    @Test
    void loginUserList() {
        // given
        UserListResponseHandler handler = new UserListResponseHandler();

        // when
        String actual = handler.run(
                RequestHeader.create(
                        "GET /user/list HTTP/1.1\n" +
                                "Host: localhost:8080\n" +
                                "Connection: keep-alive\n" +
                                "Accept: */*\n" +
                                "Cookie: logined=true"
                ),
                "",
                "".getBytes()
        );

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
        );

        // then
        assertThat(actual).isEqualTo(
                "HTTP/1.1 302 Found\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Location: /user/login_failed.html\r\n" +
                        "\r\n"
        );
    }
}