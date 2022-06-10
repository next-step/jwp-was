package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineParserTest {

    @DisplayName("GET RequestLine을 파싱할 수 있다.")
    @Test
    public void parseGetRequest() {
        String input = "GET /users HTTP/1.1";

        RequestLine actual = new RequestLineParser().parse(input);

        assertThat(actual).isEqualTo(new RequestLine("GET", "/users", "HTTP", "1.1", null));
    }

    @DisplayName("POST RequestLine을 파싱할 수 있다.")
    @Test
    public void parsePostReqeust() {
        String input = "POST /users HTTP/1.1";

        RequestLine actual = new RequestLineParser().parse(input);

        assertThat(actual).isEqualTo(new RequestLine("POST", "/users", "HTTP", "1.1", null));
    }

    @DisplayName("QueryString이 포함된 GET RequestLine을 파싱할 수 있다.")
    @Test
    public void parseQueryStringRequest() {
        String input = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        RequestLine actual = new RequestLineParser().parse(input);

        assertThat(actual).isEqualTo(new RequestLine("GET", "/users", "HTTP", "1.1", "userId=javajigi&password=password&name=JaeSung"));
    }
}
