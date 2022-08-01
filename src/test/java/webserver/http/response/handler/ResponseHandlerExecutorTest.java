package webserver.http.response.handler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.header.RequestHeader;

class ResponseHandlerExecutorTest {

    @DisplayName("요청 index가 존재하지 않다면 /index.html의 Location으로 생성되어야 한다.")
    @Test
    void createByDefault() {
        // given
        ResponseHandlerExecutor responseHandlerExecutor = new ResponseHandlerExecutor();

        // when
        String actual = responseHandlerExecutor.run(RequestHeader.create("GET /test.html HTTP/1.1"), "", new byte[0]);

        // then
        assertThat(actual).isEqualTo(
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 0\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Location: /index.html\r\n" +
                        "\r\n"
        );
    }
}
