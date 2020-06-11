package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ValidUtilsTest {

    @DisplayName("파라미터가 null인 경우 확인")
    @Test
    void notNull() {

        // given
        Object object = null;

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> ValidUtils.assertNotNull(object))
                .withMessage("파라미터는 null일 수 없습니다");

    }

    @DisplayName("파라미터가 blank인 경우 확인")
    @Test
    void notBlank() {

        // given
        String value = "";

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> ValidUtils.assertNotBlank(value, "Value must not be blank"));
    }
}