package webserver.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.common.Protocol;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseTest {

    private Response response;

    @BeforeEach
    void setUp() {
        ResponseHeader responseHeader = new ResponseHeader(Protocol.HTTP_1_1, HttpStatus.OK)
                .setContentType("text/html;charset=utf-8")
                .setLocation("/index.html")
                .setCookie("loggedIn=true");
        ResponseBody responseBody = new ResponseBody("Hello World");
        response = new Response(responseHeader, responseBody);
    }

    @DisplayName("Response 를 String 으로 변환할 수 있어야 한다.")
    @Test
    void testToString() {
        String expected = "HTTP/1.1 200 OK \r\n"
                + "Content-Type: text/html;charset=utf-8 \r\n"
                + "Location: /index.html \r\n"
                + "Set-Cookie: loggedIn=true \r\n"
                + "Content-Length: 11 \r\n"
                + "\r\n"
                + "Hello World";
        assertThat(response.toString())
                .isEqualTo(expected);
    }
}
