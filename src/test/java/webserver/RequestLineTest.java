package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RequestLineTest {

    @DisplayName("GET 요청을 받고 파싱된 값을 리턴한다.")
    @Test
    void get_request() {
        RequestLine parser = new RequestLine("GET /users HTTP/1.1");

        assertThat(parser.getMethod()).isEqualTo("GET");
        assertThat(parser.getPath()).isEqualTo("/users");
        assertThat(parser.getProtocol()).isEqualTo("HTTP");
        assertThat(parser.getVersion()).isEqualTo(1.1);

    }

    @DisplayName("POST 요청을 받고 파싱된 값을 리턴한다.")
    @Test
    void post_request() {
        RequestLine parser = new RequestLine("POST /users HTTP/1.1");

        assertThat(parser.getMethod()).isEqualTo("POST");
        assertThat(parser.getPath()).isEqualTo("/users");
        assertThat(parser.getProtocol()).isEqualTo("HTTP");
        assertThat(parser.getVersion()).isEqualTo(1.1);
    }

    @DisplayName("GET param 요청을 받고 Request Line 값을 리턴한다.")
    @Test
    void get_request_with_param() {
        RequestLine parser = new RequestLine("GET /user/create?userId=javajigi&password=pass HTTP/1.1");

        assertThat(parser.getMethod()).isEqualTo("GET");
        assertThat(parser.getPath()).isEqualTo("/user/create");
        assertThat(parser.getProtocol()).isEqualTo("HTTP");
        assertThat(parser.getVersion()).isEqualTo(1.1);
        assertThat(parser.getParams().size()).isEqualTo(2);
    }
}
