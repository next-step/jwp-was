package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.common.Protocol;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseHeaderTest {

    @DisplayName("생성한 ResponseHeader 를 String 으로 변환할 수 있어야 한다.")
    @Test
    void testToString() {
        ResponseHeader responseHeader = new ResponseHeader(Protocol.HTTP_1_1, HttpStatus.OK)
                .setContentType("text/html;charset=utf-8")
                .setContentLength(512)
                .setLocation("/index.html")
                .setCookie("loggedIn=true");

        String expected = "HTTP/1.1 200 OK \r\n"
                + "Content-Type: text/html;charset=utf-8 \r\n"
                + "Content-Length: 512 \r\n"
                + "Location: /index.html \r\n"
                + "Set-Cookie: loggedIn=true \r\n";
        assertThat(responseHeader.toString())
                .isEqualTo(expected);
    }
}
