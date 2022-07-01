package was.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeadersTest {
    @Test
    public void add() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Connection: keep-alive");
        assertThat(headers.getHeader("Connection")).isEqualTo("keep-alive");
    }
}
