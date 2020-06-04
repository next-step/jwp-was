package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RequestLineParserTest {

    @Test
    @DisplayName("요구사항 1 - GET 요청")
    void parseGet() {
        RequestLine requestLine = RequestLineParser.parse("GET /users HTTP/1.1");
        assertAll("RequestLine 객체의 책임 확인",
                () -> assertThat(requestLine.getMethodName()).isEqualTo("GET"),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocolName()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1")
        );
    }
}
