package webserver.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.request.header.RequestHeader;

class CreateHeaderTest {

    @DisplayName("GET user/create")
    @Test
    void userCreate() throws IOException {
        // given
        String request = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";

        // when
        CreateHeader createHeader = new CreateHeader();
        String header = createHeader.create(
                RequestHeader.create(request), 0
        );

        // then
        assertThat(header).isEqualTo(
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 0\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Location: /index.html\r\n" +
                        "\r\n"
        );
    }

    @DisplayName("GET user/list 로그인 한 경우")
    @Test
    void userListLogin() throws IOException {
        // given
        String request = "GET /user/list HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "Cookie: logined=true";

        // when
        CreateHeader createHeader = new CreateHeader();
        String header = createHeader.create(
                RequestHeader.create(request), 300
        );

        // then
        assertThat(header).isEqualTo(
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 300\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "\r\n"
        );
    }

    @DisplayName("GET user/list 로그인 하지 않은 경우")
    @Test
    void userListNotLogin() throws IOException {
        // given
        String request = "GET /user/list HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "Cookie: logined=false";

        // when
        CreateHeader createHeader = new CreateHeader();
        String header = createHeader.create(
                RequestHeader.create(request), 0
        );

        // then
        assertThat(header).isEqualTo(
                "HTTP/1.1 302 Found\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Location: /user/login_failed.html\r\n" +
                        "\r\n"
        );
    }
}