package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {

    @Test
    void testPath() {
        // given
        final var requestLine = new RequestLine("GET /users HTTP/1.1");

        // when
        final var actual = requestLine.getPath();

        // then
        assertThat(actual).isEqualTo("/users");
    }

    @Test
    void testProtocol() {
        // given
        final var requestLine = new RequestLine("GET /users HTTP/1.1");

        // when
        final var actual = requestLine.getProtocol();

        // then
        assertThat(actual).isEqualTo("HTTP");
    }

    @Test
    void testVersion() {
        // given
        final var requestLine = new RequestLine("GET /users HTTP/1.1");

        // when
        final var actual = requestLine.getVersion();

        // then
        assertThat(actual).isEqualTo("1.1");
    }

    @Test
    void testGETMethod() {
        // given
        final var requestLine = new RequestLine("GET /users HTTP/1.1");

        // when
        final var actual = requestLine.getMethod();

        // then
        assertThat(actual).isEqualTo(HttpMethod.GET);
    }

    @Test
    void testPOSTMethod() {
        // given
        final var requestLine = new RequestLine("POST /users HTTP/1.1");

        // when
        final var actual = requestLine.getMethod();

        // then
        assertThat(actual).isEqualTo(HttpMethod.POST);
    }
}
