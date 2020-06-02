package study;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Set 클래스 학습 테스트")
public class SetTest {
    private Set<Integer> numbers;

    @BeforeEach
    void setUp() {
        numbers = new HashSet<>();
        numbers.add(1);
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
    }

    @Test
    @DisplayName("set 의 사이즈 테스트")
    void size() {
        assertThat(numbers.size()).isEqualTo(3);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    @DisplayName("set 에 1,2,3 이 포함되어 있음")
    void contains(int containedValue) {
        assertThat(numbers.contains(containedValue)).isTrue();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("set 에 1,2,3 은 포함되어 있고, 4,5 는 포함되어 있지 않음")
    void containsSomeButOthersNot(int value, boolean isContained) {
        assertThat(numbers.contains(value)).isEqualTo(isContained);
    }

    private static Stream<Arguments> containsSomeButOthersNot() {
        return Stream.of(
                Arguments.of(1, true),
                Arguments.of(2, true),
                Arguments.of(3, true),
                Arguments.of(4, false),
                Arguments.of(5, false)
        );
    }
}
