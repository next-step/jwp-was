package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {

    @DisplayName("GET 파싱")
    @Test
    void parseGETRequestLine() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");

        String method = requestLine.getMethod();
        String path = requestLine.getPath();
        String protocol = requestLine.getProtocol();
        String version = requestLine.getVersion();

        assertAll(
                () -> assertThat(method).isEqualTo("GET"),
                () -> assertThat(path).isEqualTo("/users"),
                () -> assertThat(protocol).isEqualTo("HTTP"),
                () -> assertThat(version).isEqualTo("1.1")
        );
    }

    @DisplayName("POST 파싱")
    @Test
    void parsePOSTRequestLine() {
        RequestLine requestLine = RequestLine.parse("POST /users HTTP/1.1");

        String method = requestLine.getMethod();
        String path = requestLine.getPath();
        String protocol = requestLine.getProtocol();
        String version = requestLine.getVersion();

        assertAll(
                () -> assertThat(method).isEqualTo("POST"),
                () -> assertThat(path).isEqualTo("/users"),
                () -> assertThat(protocol).isEqualTo("HTTP"),
                () -> assertThat(version).isEqualTo("1.1")
        );
    }

    @DisplayName("QueryString 파싱")
    @Test
    void parseQueryStringRequestLine() {
        RequestLine requestLine = RequestLine.parse("GET /users?userId=dean&password=password&name=Dongchul HTTP/1.1");

        String method = requestLine.getMethod();
        String path = requestLine.getPath();
        String queryString = requestLine.getQueryString();
        String protocol = requestLine.getProtocol();
        String version = requestLine.getVersion();

        assertAll(
                () -> assertThat(method).isEqualTo("GET"),
                () -> assertThat(path).isEqualTo("/users"),
                () -> assertThat(queryString).isEqualTo("userId=dean&password=password&name=Dongchul"),
                () -> assertThat(protocol).isEqualTo("HTTP"),
                () -> assertThat(version).isEqualTo("1.1")
        );
    }
}
