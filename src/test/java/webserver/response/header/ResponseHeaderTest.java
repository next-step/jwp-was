package webserver.response.header;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.response.HttpResponseStatus;

class ResponseHeaderTest {

    @DisplayName("응답 헤더 생성 테스트")
    @Test
    void createOk() {
        // given
        ResponseHeader responseHeader = new ResponseHeader(
                "HTTP/1.1",
                HttpResponseStatus.OK
        );

        responseHeader.addContentLength(0)
                .addContentType(ContentType.HTML)
                .addLocation("/index.html");

        // when
        String actual = responseHeader.toString();

        // then
        assertThat(actual).isEqualTo(
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 0\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Location: /index.html\r\n" +
                        "\r\n"
        );
    }

    @DisplayName("응답 헤더 생성 테스트")
    @Test
    void create() {
        // given
        ResponseHeader responseHeader = new ResponseHeader(
                "HTTP/1.1",
                HttpResponseStatus.FOUND
        );

        responseHeader.addContentType(ContentType.HTML)
                .addCookie("logined=true")
                .addLocation("/index.html");

        // when
        String actual = responseHeader.toString();

        // then
        assertThat(actual).isEqualTo(
                "HTTP/1.1 302 Found\r\n" +
                        "Set-Cookie: logined=true Path=/\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Location: /index.html\r\n" +
                        "\r\n"
        );
    }
}