package http.cookie;

import http.cookie.exception.IllegalCookieHeaderFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CookieHeaderParserTest {

    @DisplayName("Cookie Header parsing하기")
    @Test
    void parse() {
        /* given */
        String cookieValue = "key1=value1; key2=value2";

        /* when */
        List<Cookie> cookies = CookieHeaderParser.parse(cookieValue);

        /* then */
        assertThat(cookies).hasSize(2);
        assertThat(cookies).contains(new Cookie("key1", "value1"), new Cookie("key2", "value2"));
    }

    @DisplayName("올바르지 않은 Cookie Header 형식일 경우 Exception")
    @ParameterizedTest
    @ValueSource(strings = {"key1", "key1=value1; key2"})
    void illegalCookieHeaderFormat(String cookieValue) { /* given */
        /* when */ /* then */
        assertThrows(IllegalCookieHeaderFormatException.class, () -> CookieHeaderParser.parse(cookieValue));
    }
}
