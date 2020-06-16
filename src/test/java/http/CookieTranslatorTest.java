package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTranslatorTest {

    private String cookieValues;

    @BeforeEach
    void setUp() {
        cookieValues = "CUSTOM_SESSION_ID=b7ba5891-4404-4102-bb68-5548648d9cec";
    }

    @Test
    @DisplayName("세션아이디 가져오기")
    void getSessionId() {
        // give
        CookieTranslator cookieTranslator = new CookieTranslator(cookieValues);
        String actual = cookieTranslator.getSessionId();
        String expected = "b7ba5891-4404-4102-bb68-5548648d9cec";
        // when
        boolean same = actual.equals(expected);
        // then
        assertThat(same).isTrue();
    }
}
