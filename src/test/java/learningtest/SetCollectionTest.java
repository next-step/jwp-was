package learningtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetCollectionTest {

    private Set<Integer> numbers;

    @BeforeEach
    void setUp() {
        numbers = new HashSet<>();
        numbers.add(1);
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
    }

    @DisplayName("Set의 size() 체크하기")
    @Test
    void size() {
        /* when */
        int size = numbers.size();

        /* then */
        assertThat(size).isEqualTo(3);
    }

    @DisplayName("Set contains()로 원소 확인하기")
    @ParameterizedTest(name = "원소 {0} : numbers Set에 포함된다")
    @ValueSource(ints = {1, 2, 3}) /* when */
    void contains(int number) {
        /* then */
        assertThat(numbers.contains(number)).isTrue();
    }

    @DisplayName("CsvSource를 사용하여 Set contains() 원소 확인하기")
    @ParameterizedTest(name = "원소 {0} : numbers Set에 포함되는지 여부 - {1}")
    @CsvSource(value = {"1:true", "2:true", "3:true", "4:false", "5:false"}, delimiter = ':') /* when */
    void contains2(int number, boolean expected) {
        /* then */
        assertEquals(numbers.contains(number), expected);
    }
}

