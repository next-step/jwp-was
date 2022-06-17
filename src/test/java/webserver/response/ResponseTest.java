package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseTest {


    @DisplayName("Response 를 bytes 로 변환할 수 있어야 한다.")
    @Test
    void testToString() {
        ResponseHeader responseHeader = new ResponseHeader(HttpStatus.OK)
                .setContentType("text/html;charset=utf-8")
                .setLocation("/index.html")
                .setCookie("loggedIn=true");
        ResponseBody responseBody = ResponseBody.from("Hello World");
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

    @DisplayName("body 가 없는 Response 를 만들 수 있다.")
    @Test
    void testEmptyBody() {
        Response response = new Response(
                new ResponseHeader(HttpStatus.FOUND).setLocation("/index.html")
        );
        String actual = new String(response.toBytes());
        String expected = "HTTP/1.1 302 Found \r\n"
                + "Location: /index.html \r\n"
                + "\r\n";
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Response 의 Cookie 를 변경할 수 있다.")
    @Test
    void setCookie() {
        ResponseHeader responseHeader = new ResponseHeader().setCookie("loggedIn=true");
        Response response = new Response(responseHeader);

        String actual = new String(response.setCookie("loggedIn=false").toBytes());
        String expected = "HTTP/1.1 200 OK \r\n"
                + "Set-Cookie: loggedIn=false \r\n"
                + "Content-Length: 0 \r\n"
                + "\r\n";
        assertThat(actual).isEqualTo(expected);
    }
}
