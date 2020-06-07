package http;

import org.junit.jupiter.api.Test;
import utils.HttpStringBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeadersTest {
    @Test
    public void createTest() {
        String headerString = HttpStringBuilder.builder()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0")
                .buildHeaders();

        HttpHeaders httpHeaders = HttpHeaders.from(headerString);

        assertThat(httpHeaders.getHeader(HttpHeaders.CONTENT_TYPE)).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(httpHeaders.getHeader(HttpHeaders.ACCEPT)).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(httpHeaders.getHeader(HttpHeaders.USER_AGENT)).isEqualTo("Mozilla/5.0");
    }
}
