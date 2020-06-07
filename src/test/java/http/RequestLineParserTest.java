package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RequestLineParser 테스트")
public class RequestLineParserTest {

    @DisplayName("RequestLine을 파싱한다.")
    @Test
    void parse() {
        RequestLine requestLine = RequestLineParser.parse("GET /users HTTP/1.1");
        assertThat(requestLine).isEqualTo(new RequestLine("GET", "/users", "HTTP/1.1"));
    }

}
