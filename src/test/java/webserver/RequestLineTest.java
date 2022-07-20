package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class RequestLineTest {

    @DisplayName("빈 문자열은 파싱할 수 없다.")
    @ParameterizedTest(name = "#{index}: [{arguments}]")
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void empty_strings_cannot_be_parsed(String requestLine) {
        assertThatThrownBy(() -> RequestLine.parse(requestLine))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("빈 문자열은 파싱할 수 없습니다.");
    }

    @DisplayName("GET 요청 파싱")
    @Test
    void parsing_a_get_request() {
        final String requestLine = "GET /users HTTP/1.1";

        final RequestLine parse = RequestLine.parse(requestLine);

        assertAll(
            () -> assertThat(parse.getMethod()).isSameAs(HttpMethod.GET),
            () -> assertThat(parse.getPath()).isEqualTo("/users"),
            () -> assertThat(parse.getProtocol()).isEqualTo("HTTP"),
            () -> assertThat(parse.getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("QueryString을 포함한 GET 요청 파싱")
    @Test
    void parsing_a_get_request_with_query_string() {
        final String requestLine = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        final RequestLine parse = RequestLine.parse(requestLine);

        assertAll(
            () -> assertThat(parse.getMethod()).isSameAs(HttpMethod.GET),
            () -> assertThat(parse.getPath()).isEqualTo("/users"),
            () -> assertThat(parse.getProtocol()).isEqualTo("HTTP"),
            () -> assertThat(parse.getVersion()).isEqualTo("1.1"),
            () -> assertThat(parse.getQueryString()).isEqualTo("userId=javajigi&password=password&name=JaeSung")
        );
    }

    @DisplayName("POST 요청 파싱")
    @Test
    void parsing_a_post_request() {
        final String requestLine = "POST /users HTTP/1.1";

        final RequestLine parse = RequestLine.parse(requestLine);

        assertAll(
            () -> assertThat(parse.getMethod()).isSameAs(HttpMethod.POST),
            () -> assertThat(parse.getPath()).isEqualTo("/users"),
            () -> assertThat(parse.getProtocol()).isEqualTo("HTTP"),
            () -> assertThat(parse.getVersion()).isEqualTo("1.1")
        );
    }

}
