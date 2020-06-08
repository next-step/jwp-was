package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @DisplayName("파라미터가 empty인 경우 확인")
    @Test
    void isBlank_empty() {

        // given
        String value = "";

        // when
        boolean isBlank = StringUtils.isBlank(value);

        // then
        assertThat(isBlank).isTrue();
    }

    @DisplayName("파라미터가 null인 경우 확인")
    @Test
    void isBlank_null() {

        // given
        String value = null;

        // when
        boolean isBlank = StringUtils.isBlank(value);

        // then
        assertThat(isBlank).isTrue();
    }

    @DisplayName("파라미터가 empty, null이 아닌 경우 확인")
    @Test
    void isBlank_() {

        // given
        String value = "NotBlank";

        // when
        boolean isBlank = StringUtils.isBlank(value);

        // then
        assertThat(isBlank).isFalse();
    }
}