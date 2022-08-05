package webserver.http.response.handler.get;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.header.RequestHeader;
import webserver.http.response.handler.get.HomeResponseHandler;

@DisplayName("HomeResponseHandler 테스트")
class HomeResponseHandlerTest {

    @DisplayName("호출 시 응답 테스트")
    @Test
    void createTest() {
        // given
        HomeResponseHandler homeResponseHandler = new HomeResponseHandler();

        // when
        String actual = homeResponseHandler.run(
                RequestHeader.create("GET /index.html HTTP/1.1"), "", new byte[0]
        ).toString();

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
