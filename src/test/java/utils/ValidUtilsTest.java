package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ValidUtilsTest {

    @DisplayName("파라미터가 null인지 확인")
    @ParameterizedTest
    @NullSource
    void notNull(Object object) {

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ValidUtils.assertNotNull(object));

    }

    @DisplayName("파라미터가 blank인지 확인")
    @ParameterizedTest
    @NullAndEmptySource
    void notNullAndEmpty(String value) {

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ValidUtils.assertNotBlank(value, "Value must not be blank"));

    }
}