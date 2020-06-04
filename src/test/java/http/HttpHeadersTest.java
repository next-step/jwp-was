package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeadersTest {
    @Test
    public void createTest() {
        String headerString = "Content-Type: application/json\n" +
                "Accept: application/json\n" +
                "User-Agent: Mozilla/5.0";

        HttpHeaders httpHeaders = HttpHeaders.from(headerString);

        assertThat(httpHeaders.getHeader("Content-Type")).isEqualTo("application/json");
        assertThat(httpHeaders.getHeader("Accept")).isEqualTo("application/json");
        assertThat(httpHeaders.getHeader("User-Agent")).isEqualTo("Mozilla/5.0");
    }
}
