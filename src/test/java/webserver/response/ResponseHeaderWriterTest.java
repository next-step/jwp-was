package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResponseHeaderWriterTest {

    @DisplayName("200 리턴 헤더")
    @Test
    void successTest() {
        // given
        ResponseHeaderWriter responseHeaderWriter = new ResponseHeaderWriter();

        // when
        String actual = responseHeaderWriter.response200("HTTP/1.1", 0);

        // then
        assertThat(actual).isEqualTo(
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Content-Length: 0\r\n" +
                        "\r\n"
        );
    }

    @DisplayName("302 리턴 헤더")
    @Test
    void foundTest() {
        // given
        ResponseHeaderWriter responseHeaderWriter = new ResponseHeaderWriter();

        // when
        String actual = responseHeaderWriter.response302("HTTP/1.1", "localhost:8080");

        // then
        assertThat(actual).isEqualTo(
                "HTTP/1.1 302 Found\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Location: http://localhost:8080/index.html\r\n" +
                        "\r\n"
        );
    }
}