package utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class StringUtilsTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\n", "\t"})
    void empty(String val) {
        assertThat(StringUtils.isEmpty(val)).isEqualTo(true);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc ", "\na", "\ta"})
    void notEmpty(String val) {
        assertThat(StringUtils.isNotEmpty(val)).isEqualTo(true);
    }
}
