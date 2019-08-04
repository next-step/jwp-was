package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class HttpRequestTest {

    @Test
    @DisplayName("Parse request")
    void parse() throws IOException {
        BufferedReader br = createInputStream(
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        );

        HttpRequest request = HttpRequest.parse(br);

        assertThat(request.getPath()).isEqualTo("/index.html");
        assertThat(request.getHeaders())
                .hasSize(3)
                .containsEntry("Host", "localhost:8080")
                .containsEntry("Connection", "keep-alive")
                .containsEntry("Accept", "*/*");
    }

    @Test
    @DisplayName("Parse POST request with body")
    void parsePostRequest() throws IOException {
        BufferedReader br = createInputStream(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 93",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*",
                "",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
        );

        HttpRequest request = HttpRequest.parse(br);

        assertThat(request.getBody())
                .hasSize(4)
                .containsEntry("userId", "javajigi")
                .containsEntry("password", "password")
                .containsEntry("name", "박재성")
                .containsEntry("email", "javajigi@slipp.net");
    }

    @Test
    @DisplayName("If first line is not Request-Line, should fail to parse")
    void parseFromInvalidRequest_fail() {
        BufferedReader br = createInputStream(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        );

        assertThatIllegalArgumentException().isThrownBy(() -> HttpRequest.parse(br));
    }

    public static BufferedReader createInputStream(String... strings) {
        String input = Arrays.stream(strings).collect(Collectors.joining(System.lineSeparator()));
        return new BufferedReader(new StringReader(input));
    }
}
