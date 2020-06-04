package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    @DisplayName("RequestLine 생성 - 전체 Parsing")
    @Test
    void create() {
        //given
        String request = "GET /users HTTP/1.1";

        //when
        RequestLine requestLine = RequestLine.parse(request);

        //then
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("RequestLine 생성 - GET with QueryStrings")
    @Test
    void parseQueryString() {
        //given
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        //when
        RequestLine parsedLine = RequestLine.parse(request);

        //then
        assertThat(parsedLine.getQueryStrings()).hasSize(3);
        assertThat(parsedLine.getQueryStrings().get("userId")).isEqualTo("javajigi");
        assertThat(parsedLine.getQueryStrings().get("password")).isEqualTo("password");
        assertThat(parsedLine.getQueryStrings().get("name")).isEqualTo("JaeSung");
    }
}