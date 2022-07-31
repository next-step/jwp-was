package webserver.http.response.handler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.header.RequestHeader;

@DisplayName("DefaultResponseHandler 테스트")
class DefaultResponseHandlerTest {

    @DisplayName("호출 시 응답 테스트")
    @Test
    void createTest() {
        // given
        DefaultResponseHandler defaultResponseHandler = new DefaultResponseHandler();

        // when
        String actual = defaultResponseHandler.run(RequestHeader.create("GET /test.html HTTP/1.1"), 0);

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
