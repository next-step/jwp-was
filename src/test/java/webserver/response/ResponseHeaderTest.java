package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.common.Protocol;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ResponseHeaderTest {

    @DisplayName("생성한 ResponseHeader 를 bytes 로 변환할 수 있어야 한다.")
    @Test
    void testToString() {
        ResponseHeader responseHeader = new ResponseHeader(Protocol.HTTP_1_1, HttpStatus.OK)
                .setContentType("text/html;charset=utf-8")
                .setContentLength(512)
                .setLocation("/index.html")
                .setCookie("loggedIn=true");

        String actual = new String(responseHeader.toBytes());
        String expected = "HTTP/1.1 200 OK \r\n"
                + "Content-Type: text/html;charset=utf-8 \r\n"
                + "Content-Length: 512 \r\n"
                + "Location: /index.html \r\n"
                + "Set-Cookie: loggedIn=true \r\n"
                + "\r\n";
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("ResponseHeader 는 HTTP1.1 과 OK status 를 디폴트로 가진다.")
    @Test
    void testDefault() {
        assertAll(
                () -> assertThat(new String(new ResponseHeader().toBytes()))
                        .isEqualTo("HTTP/1.1 200 OK \r\n\r\n"),
                () -> assertThat(new String(new ResponseHeader(HttpStatus.FOUND).toBytes()))
                        .isEqualTo("HTTP/1.1 302 Found \r\n\r\n")
        );
    }
}
