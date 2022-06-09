package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.exception.IllegalRequestLineException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestLineTest {

    @DisplayName("RequestLine 을 파싱해서, method, path, query string, protocol 정보를 가져올 수 있다.")
    @Test
    void from() {
        RequestLine requestLine = RequestLine.from("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getQuery("userId")).isEqualTo("javajigi"),
                () -> assertThat(requestLine.getQuery("password")).isEqualTo("password"),
                () -> assertThat(requestLine.getQuery("name")).isEqualTo("JaeSung"),
                () -> assertThat(requestLine.getProtocol()).isEqualTo(Protocol.HTTP_1_1)
        );
    }

    @DisplayName("request line 이 null 일 경우, IllegalRequestLineException 이 발생한다.")
    @Test
    void emptyRequestLine() {
        assertThatThrownBy(
                () -> RequestLine.from(null)
        ).isInstanceOf(IllegalRequestLineException.class);
    }

    @DisplayName("부적절한 request line 일 경우, IllegalRequestLineException 이 발생한다.")
    @Test
    void illegalRequestLine() {
        assertThatThrownBy(
                () -> RequestLine.from("GET /users")
        ).isInstanceOf(IllegalRequestLineException.class);
    }
}
