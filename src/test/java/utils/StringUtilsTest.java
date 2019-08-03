package utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

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
}