package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookiesTest {

    @DisplayName("Cookies 객체를 생성할 수 있다")
    @Test
    public void create() {
        String input = "logined=true; expire=2023-01-01";

        Cookies actual = Cookies.from(input);

        assertThat(actual.get("logined").getValue()).isEqualTo("true");
        assertThat(actual.get("expire").getValue()).isEqualTo("2023-01-01");
    }
}