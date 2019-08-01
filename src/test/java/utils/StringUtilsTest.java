package utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import utils.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " ", "      "})
    void isBlank(final String string) {
        // when
        final boolean blank = StringUtils.isBlank(string);

        // then
        assertThat(blank).isTrue();
    }
}
