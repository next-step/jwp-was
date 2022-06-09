package webserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestLineTest {

    @Test
    void get() {
        RequestLine requestLine = new RequestLine("GET /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1");
    }

    @Test
    void getWithParameters() {
        QueryString queryString = new RequestLine("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1").toQueryString();

        assertThat(queryString.get("userId")).isEqualTo("javajigi");
        assertThat(queryString.get("password")).isEqualTo("password");
        assertThat(queryString.get("name")).isEqualTo("JaeSung");
    }

    @Test
    void post() {
        RequestLine requestLine = new RequestLine("POST /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1");
    }

    @Test
    void emptyText() {
        assertThatThrownBy(
                () -> new RequestLine(null)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET", "GET /users", "GET /users HTTP", "GET /users HTTP/"})
    void illegalText(String text) {
        assertThatThrownBy(
                () -> new RequestLine(text)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
