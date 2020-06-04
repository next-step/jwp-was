package http.requestline;

import http.requestline.exception.IllegalRequestLineParsingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestLineParserTest {

    @DisplayName("Request Line Parsing 하기")
    @Test
    void parse() {
        /* given */
        String request = "GET /users HTTP/1.1";

        /* when */
        RequestLine requestLine = RequestLineParser.parse(request);

        /* then */
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocolName()).isEqualTo("HTTP");
        assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1");
    }

    @DisplayName("RequestLine를 공백 기준으로 나눴을 때 세 파트가 나오지 않으면 IllegalRequestLineParsingException")
    @ParameterizedTest
    @ValueSource(strings = {"GET /users", "GET", "GET /users HTTP/1.1 temp"})
    void parse_IllegalArgumentException(String request) { /* given */
        /* when */ /* then */
        assertThrows(IllegalRequestLineParsingException.class, () -> RequestLineParser.parse(request));
    }
}
