package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RequestLineParserTest {

    @DisplayName("GET 요청을 받고 파싱된 값을 리턴한다.")
    @Test
    void get_request() {
        RequestLineParser parser = new RequestLineParser("GET /users HTTP/1.1");

        assertThat(parser.getMethod()).isEqualTo("GET");
        assertThat(parser.getPath()).isEqualTo("/users");
        assertThat(parser.getProtocol()).isEqualTo("HTTP");
        assertThat(parser.getVersion()).isEqualTo(1.1);

    }
}
