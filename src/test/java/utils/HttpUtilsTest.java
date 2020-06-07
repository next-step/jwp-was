package utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HttpUtilsTest {

    @Test
    void mimeType() {
        assertThat(HttpUtils.getMimeType("product.png")).isEqualTo("image/png");
        assertThat(HttpUtils.getMimeType("bootstrap.js")).isEqualTo("application/javascript");
    }

}
