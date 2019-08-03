package webserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    private static BufferedReader bufferedReader;

    @BeforeAll
    static void mockingBufferedReader() throws IOException {
        bufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReader.readLine())
                .thenReturn(
                        "GET /index.html HTTP/1.1",
                        "Host: localhost:8080",
                        "Connection: keep-alive",
                        "Accept: */*",
                        ""
                );
    }

    @Test
    void httpHeadersTest() throws IOException {
        HttpRequest httpRequest = HttpRequest.parse(bufferedReader);

        HttpHeaders httpHeaders = httpRequest.getHeaders();
        assertThat(httpHeaders.get("Host")).isEqualTo("localhost:8080");
        assertThat(httpHeaders.get("Connection")).isEqualTo("keep-alive");
        assertThat(httpHeaders.get("Accept")).isEqualTo("*/*");
    }
}
