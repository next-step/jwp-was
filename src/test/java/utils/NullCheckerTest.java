package utils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NullCheckerTest {

    @DisplayName("입력 값에 null 이 하나라도 있으면 예외를 던지는 기능을 테스트한다.")
    @Test
    void checkTest1() {
        assertThatThrownBy(
            () -> NullChecker.requireNonNull("test", null, "test2")
        ).isInstanceOf(NullPointerException.class);
    }

    @DisplayName("입력 값에 null 이 하나일 때 예외를 던지는 기능을 테스트한다.")
    @Test
    void checkTest2() {
        assertThatThrownBy(
            () -> NullChecker.requireNonNull(null)
        ).isInstanceOf(NullPointerException.class);
    }

}
