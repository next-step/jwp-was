package webserver.http.response.handler.post;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import webserver.http.request.header.RequestHeader;
import webserver.http.response.handler.post.UserCreatePostResponseHandler;

@DisplayName("UserCreateResponseHandler 테스트")
class UserCreatePostResponseHandlerTest {

    @DisplayName("유저 생성 요청")
    @Test
    void createUser() {
        // given
        UserCreatePostResponseHandler handler = new UserCreatePostResponseHandler();

        // when
        String actual = handler.run(
                RequestHeader.create("POST /user/create HTTP/1.1\n"),
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net",
                new byte[0]
        );

        // then
        assertThat(DataBase.findUserById("javajigi")).isNotNull();
        assertThat(actual).isEqualTo(
                "HTTP/1.1 302 Found\r\n" +
                        "Content-Length: 0\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Location: /index.html\r\n" +
                        "\r\n"
        );
    }
}
