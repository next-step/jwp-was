package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlTypeTest {
    @Test
    void LOGIN_USER() {
        String urlPath = "/user/login";
        assertThat(UrlType.of(urlPath)).isEqualTo(UrlType.LOGIN_USER);
    }
}
