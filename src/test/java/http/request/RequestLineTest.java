package http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestLineTest {

    @DisplayName("Requestline GET 객체를 생성할 수 있다.")
    @Test
    void GET_객체_생성() {
        var line = "GET /users HTTP/1.1";

        var requestLine = new RequestLine(line);

        assertAll(
            () -> assertThat(requestLine.getMethod()).isEqualTo("GET"),
            () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
            () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
            () -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("RequestLine POST 객체를 생성할 수 있다.")
    @Test
    void POST_객체_생성() {
        var line = "POST /users HTTP/1.1";

        var requestLine = new RequestLine(line);

        assertAll(
            () -> assertThat(requestLine.getMethod()).isEqualTo("POST"),
            () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
            () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
            () -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("Request Parameter를 읽을 수 있다.")
    @Test
    void getQueryParams() {
        var line = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        var requestLine = new RequestLine(line);

        var params = requestLine.getQueryParams();
        assertAll(
            () -> assertThat(params.get("userId")).isEqualTo("javajigi"),
            () -> assertThat(params.get("password")).isEqualTo("password"),
            () -> assertThat(params.get("name")).isEqualTo("JaeSung")
        );
    }
}