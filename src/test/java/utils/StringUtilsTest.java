package utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"hi", "hello   ", "     bye"})
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input) {
        assertFalse(StringUtils.isBlank(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isBlank_ShouldReturnTrueForNullInputs(String input) {
        assertTrue(StringUtils.isBlank(input));
    }

    @Test
    void escape() throws UnsupportedEncodingException {
        String result = StringUtils.escape("korkorna@gmail.com");
        assertThat(result).isEqualTo("korkorna%40gmail.com");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void escape_ShuoldReturnNullOrBlankForNullOrBlankStrings(String input) throws UnsupportedEncodingException {
        assertThat(StringUtils.escape(input)).isEqualTo("");
    }

    @Test
    void unescape() throws UnsupportedEncodingException {
        String result = StringUtils.unescape("korkorna%40gmail%2ecom");
        assertThat(result).isEqualTo("korkorna@gmail.com");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void unescape_ShuoldReturnNullOrBlankForNullOrBlankStrings(String input) throws UnsupportedEncodingException {
        assertThat(StringUtils.unescape(input)).isEqualTo("");
    }
}