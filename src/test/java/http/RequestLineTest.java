package http;

import http.request.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RequestLineTest {

    @DisplayName("RequestLine 객체 생성 성공 - GET")
    @Test
    void requestLineParsingSuccess_GET() {
        String url = "GET /users HTTP/1.1";
        RequestLine requestLine = new RequestLine(url);

        assertAll(
                () -> assertThat(requestLine.getHttpMethod()).isEqualTo("GET"),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocolType()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("RequestLine 객체 생성 실패 - GET")
    @Test
    void requestLineParsingFail_GET() {
        String url = "Get /users HTTP/1.1";

        assertThatThrownBy(
                () -> new RequestLine(url)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("RequestLine 객체 생성 성공 - POST")
    @Test
    void requestLineParsingSuccess_POST() {
        String url = "POST /users HTTP/1.1";
        RequestLine requestLine = new RequestLine(url);

        assertAll(
                () -> assertThat(requestLine.getHttpMethod()).isEqualTo("POST"),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocolType()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("RequestLine 객체 생성 실패 - POST")
    @Test
    void requestLineParsingFail_POST() {
        String url = "Post /users HTTP/1.1";

        assertThatThrownBy(
                () -> new RequestLine(url)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
