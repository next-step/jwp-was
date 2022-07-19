package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("RequestLineParser 단위 테스트")
public class RequestLineParserTest {
    @Test
    @DisplayName("RequestLine을 파싱하여 HttpRequest를 얻는다.")
    void parse() {
        // given
        RequestLineParser requestLineParser = new RequestLineParser();

        // when
        HttpRequest httpRequest = requestLineParser.parse("GET /users HTTP/1.1");

        // then
        assertThat(httpRequest).isEqualTo(
                new HttpRequest(new HttpMethod("GET"), new HttpPath("/users"), new HttpProtocol("HTTP/1.1")));
    }
}
