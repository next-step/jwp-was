package webserver.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CookiesTest {

    private Cookies cookies;

    @BeforeEach
    void setUp() {
        this.cookies = Cookies.of("cook1=1; cook3=3; cook5=5");
    }

    @Test
    void createTest() {
        Cookies cookies = Cookies.of("yummy_cookie=choco; tasty_cookie=strawberry");

        assertThat(cookies.get("yummy_cookie")).isEqualTo("choco");
        assertThat(cookies.get("tasty_cookie")).isEqualTo("strawberry");
    }

    @DisplayName("유효하지 않은 키값으로 쿠키를 빼내려하면 실패한다.")
    @Test
    void invalidCookieKeyTest() {
        assertThatThrownBy(
            () -> cookies.get(null)
        ).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(
            () -> cookies.get("")
        ).isInstanceOf(IllegalArgumentException.class);
    }

}
