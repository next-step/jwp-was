package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.common.Protocol;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseTest {


    @DisplayName("Response 를 bytes 로 변환할 수 있어야 한다.")
    @Test
    void testToString() {
        ResponseHeader responseHeader = new ResponseHeader(Protocol.HTTP_1_1, HttpStatus.OK)
                .setContentType("text/html;charset=utf-8")
                .setLocation("/index.html")
                .setCookie("loggedIn=true");
        ResponseBody responseBody = new ResponseBody("Hello World".getBytes());
        Response response = new Response(responseHeader, responseBody);

        String actual = new String(response.toBytes());
        String expected = "HTTP/1.1 200 OK \r\n"
                + "Content-Type: text/html;charset=utf-8 \r\n"
                + "Location: /index.html \r\n"
                + "Set-Cookie: loggedIn=true \r\n"
                + "Content-Length: 11 \r\n"
                + "\r\n"
                + "Hello World";
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Not Implemented 일 경우 501 을 응답해야 한다.")
    @Test
    void testNotImplemented() {
        String actual = new String(Response.createNotImplemented().toBytes());
        String expected = "HTTP/1.1 501 Not Implemented \r\n"
                + "Content-Type: text/html;charset=utf-8 \r\n"
                + "Content-Length: 19 \r\n"
                + "\r\n"
                + "Not Implemented Yet";
        assertThat(actual).isEqualTo(expected);
    }
}
