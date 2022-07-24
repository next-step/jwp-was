package webserver.http.request.header;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestHeadersTest {
    @Test
    void create_and_get() {
        HttpRequestHeaders httpRequestHeaders = new HttpRequestHeaders(List.of(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        ));

        assertThat(httpRequestHeaders.get("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequestHeaders.get("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequestHeaders.get("Accept")).isEqualTo("*/*");
    }
}
