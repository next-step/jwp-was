package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("검증")
class AssertTest {

    private static final String ERROR_MESSAGE = "error";

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
                .isThrownBy(() -> Assert.notNull(null, ERROR_MESSAGE))
                .withMessage(ERROR_MESSAGE);
    }

    @Test
    @DisplayName("빈 문자 검증")
    void hasText() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Assert.hasText("", ERROR_MESSAGE))
                .withMessage(ERROR_MESSAGE);
    }
}
