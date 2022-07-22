package utils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class NullCheckerTest {

    @Test
    void checkTest1() {
        assertThatThrownBy(
            () -> NullChecker.requireNonNull("test", null, "test2")
        ).isInstanceOf(NullPointerException.class);
    }

    @Test
    void checkTest2() {
        assertThatThrownBy(
            () -> NullChecker.requireNonNull(null)
        ).isInstanceOf(NullPointerException.class);
    }

}
