package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTranslatorTest {

    private String cookieValues;

    @BeforeEach
    void setUp() {
        cookieValues = "JSESSIONID=2D1DD994C93709F4551726B01B720252; _ga=GA1.1.35327423.1583978820; logined=true";
    }

    @Test
    @DisplayName("배열 크기 확인")
    void size() {
        // give
        CookieTranslator cookieTranslator = new CookieTranslator(cookieValues);
        int actualSize = cookieTranslator.getCookieValues().length;
        // when then
        assertThat(actualSize).isEqualTo(3);
    }

    @Test
    @DisplayName("쿠키 로그인 여부")
    void isLogined() {
        // give
        CookieTranslator cookieTranslator = new CookieTranslator(cookieValues);
        boolean actual = cookieTranslator.isLogined();
        // when then
        assertThat(actual).isTrue();
    }
}
