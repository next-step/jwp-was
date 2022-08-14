package utils;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class AssertTest {
    @Test
    @DisplayName("생성 불가")
    void instance_thrownAssertionError() {
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> ReflectionUtils.newInstance(Assert.class));
    }

    @Test
    @DisplayName("null 검증")
    void notNull() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Assert.notNull(null, "error"))
                .withMessage("error");
    }

    @Test
    @DisplayName("empty Map 검증")
    void notEmpty() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Assert.notEmpty(null, "error"))
                .withMessage("error");
    }
}