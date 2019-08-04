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
    @DisplayName("If first line is not Request-Line, should fail to parse")
    void parseFromInvalidRequest_fail() {
        BufferedReader br = createInputStream(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        );

        assertThatIllegalArgumentException().isThrownBy(() -> HttpRequest.parse(br));
    }

    private static BufferedReader createInputStream(String... strings) {
        String input = Arrays.stream(strings).collect(Collectors.joining(System.lineSeparator()));
        return new BufferedReader(new StringReader(input));
    }
}
