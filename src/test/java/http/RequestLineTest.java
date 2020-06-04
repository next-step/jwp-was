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
        assertThat(requestLine.getMethod()).isEqualTo(Method.GET);
        assertThat(requestLine.getPath()).isEqualTo(new Path("/users"));
        assertThat(requestLine.getProtocolAndVersion()).isEqualTo(new ProtocolAndVersion(Protocol.HTTP, "1.1"));
    }

    @DisplayName("RequestLine 생성 - GET with QueryStrings")
    @Test
    void parseQueryString() {
        //given
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        //when
        RequestLine parsedLine = RequestLine.parse(request);

        //then
        assertThat(parsedLine.getPath().getQueryStrings()).hasSize(3);
        assertThat(parsedLine.getPath().getQueryStrings().get("userId")).isEqualTo("javajigi");
        assertThat(parsedLine.getPath().getQueryStrings().get("password")).isEqualTo("password");
        assertThat(parsedLine.getPath().getQueryStrings().get("name")).isEqualTo("JaeSung");
    }
}