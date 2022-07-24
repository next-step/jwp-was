package http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RequestLineTest {

    @DisplayName("Requestline GET 객체를 생성할 수 있다.")
    @Test
    void GET_객체_생성() {
        var line = "GET /users HTTP/1.1";

        var requestLine = new RequestLine(line);

        assertAll(() -> assertThat(requestLine.getMethod()).isEqualTo("GET"),
            () -> assertThat(requestLine.getUrl()).isEqualTo("/users"),
            () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
            () -> assertThat(requestLine.getVersion()).isEqualTo("1.1"));
    }

    @DisplayName("RequestLine POST 객체를 생성할 수 있다.")
    @Test
    void POST_객체_생성() {
        var line = "POST /users HTTP/1.1";

        var requestLine = new RequestLine(line);

        assertAll(() -> assertThat(requestLine.getMethod()).isEqualTo("POST"),
            () -> assertThat(requestLine.getUrl()).isEqualTo("/users"),
            () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
            () -> assertThat(requestLine.getVersion()).isEqualTo("1.1"));
    }

    @DisplayName("Request Parameter를 읽을 수 있다.")
    @Test
    void getQueryParams() {
        var line = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        var requestLine = new RequestLine(line);

        var params = requestLine.getQueryParams();
        assertAll(() -> assertThat(params.get("userId")).isEqualTo("javajigi"),
            () -> assertThat(params.get("password")).isEqualTo("password"),
            () -> assertThat(params.get("name")).isEqualTo("JaeSung"));
    }

    @DisplayName("RequestLine을 통해 정적파일을 확인할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"/users?userId=javajigi&password=password&name=JaeSung,false", "/index.html,true"})
    void isStaticFile(String url, boolean expect) {
        var line = "GET " + url + " HTTP/1.1";

        var requestLine = new RequestLine(line);

        assertThat(requestLine.isStaticFile()).isEqualTo(expect);
    }
}