package request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


class RequestLineTest {

    @DisplayName("GET RequestLine parsing 성공")
    @Test
    void createGetRequestLine_success() {
        String request = "GET /users HTTP/1.1";
        RequestLine requestLine = new RequestLine(request);

        assertAll(
            () -> assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET.name()),
            () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
            () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
            () -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("GET RequestLine parsing 실패")
    @Test
    void createGetRequestLine_fail() {
        String request = "GETT /users HTTP/1.1";
        assertThatThrownBy(
            () -> new RequestLine(request)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("POST RequestLine parsing 성공")
    @Test
    void createPostRequestLine_success() {
        String request = "POST /users HTTP/1.1";
        RequestLine requestLine = new RequestLine(request);

        assertAll(
            () -> assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.POST.name()),
            () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
            () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
            () -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("POST RequestLine parsing 실패")
    @Test
    void createPostRequestLine_fail() {
        String request = "POSTT /users HTTP/1.1";
        assertThatThrownBy(
            () -> new RequestLine(request)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
