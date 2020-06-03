package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpMethodTest {

    @Test
    void findMethod() {
        HttpMethod httpMethod = HttpMethod.findByName("GET");
        assertThat(httpMethod).isEqualTo(HttpMethod.GET);
    }
}
