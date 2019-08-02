package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @DisplayName("빈 값인지 확인한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " ", "      "})
    void isBlank(final String string) {
        // when
        final boolean blank = StringUtils.isBlank(string);

        // then
        assertThat(blank).isTrue();
    }

    @DisplayName("빈 값이 아닌지 확인한다.")
    @ParameterizedTest
    @ValueSource(strings = {"asd", "sad ", " dsf dsfdsg fdfdgdf   "})
    void isNotBlank(final String string) {
        // when
        final boolean notBlank = StringUtils.isNotBlank(string);

        // then
        assertThat(notBlank).isTrue();
    }
}
