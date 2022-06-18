package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    @DisplayName("GET RequestLine을 파싱할 수 있다.")
    @Test
    public void parseGetRequest() {
        String input = "GET /users HTTP/1.1";

        RequestLine actual = RequestLine.from(input);

        assertThat(actual).isEqualTo(new RequestLine(HttpMethod.valueOf("GET"), Uri.from("/users"), Protocol.from("HTTP/1.1")));
    }

    @DisplayName("POST RequestLine을 파싱할 수 있다.")
    @Test
    public void parsePostReqeust() {
        String input = "POST /users HTTP/1.1";

        RequestLine actual = RequestLine.from(input);

        assertThat(actual).isEqualTo(new RequestLine(HttpMethod.valueOf("POST"), Uri.from("/users"), Protocol.from("HTTP/1.1")));
    }

    @DisplayName("QueryString이 포함된 GET RequestLine을 파싱할 수 있다.")
    @Test
    public void parseQueryStringRequest() {
        String input = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        RequestLine actual = RequestLine.from(input);

        assertThat(actual).isEqualTo(new RequestLine(HttpMethod.valueOf("GET"), Uri.from("/users?userId=javajigi&password=password&name=JaeSung"), Protocol.from("HTTP/1.1")));
    }
}
