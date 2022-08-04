package webserver.http.response.header;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.response.HttpResponseStatus;

class ResponseHeaderTest {

    @DisplayName("ResponseHeader sessionId set 테스트")
    @Test
    void create() {
        ResponseHeader responseHeader = new ResponseHeader("HTTP/1.1", HttpResponseStatus.OK);
        responseHeader.addCookieSessionId("123");

        assertThat(responseHeader.toString()).isEqualTo(
                "HTTP/1.1 200 OK\r\n" +
                        "Set-Cookie: sessionId=123 Path=/\r\n" +
                        "\r\n"
        );
    }
}