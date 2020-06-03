package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("문자열 클래스 학습 테스트")
public class StringTest {

    @Test
    @DisplayName(",로 split 했을 때 1과 2로 잘 분리되는지 확인")
    void split() {
        String[] splits = "1,2".split(",");

        assertThat(splits).containsExactly("1", "2");
    }

    @Test
    @DisplayName("1을 ,로 분리했을때 1이 나오는지")
    void splitNoSplitContainedString() {
        String[] splits = "1".split(",");

        assertThat(splits).containsExactly("1");
    }

    @Test
    @DisplayName("부분 문자열")
    void subString() {
        String origin = "(1,2)";
        String subString = origin.substring(origin.indexOf('(') + 1, origin.indexOf(')'));

        assertThat(subString).isEqualTo("1,2");
    }


    @ParameterizedTest
    @MethodSource
    @DisplayName("문자열의 특정 위치의 문자를 잘 가져오는지 테스트")
    void charAt(int index, char expectedChar) {
        String abc = "abc";

        assertThat(abc.charAt(index)).isEqualTo(expectedChar);
    }

    private static Stream<Arguments> charAt() {
        return Stream.of(
                Arguments.of(0, 'a'),
                Arguments.of(1, 'b'),
                Arguments.of(2, 'c')
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 3})
    @DisplayName("문자열의 범위 밖을 조회했을때 익셉션이 발생하는지")
    void charAtOutOfIndex(int index) {
        String abc = "abc";

        assertThatThrownBy(() -> abc.charAt(index)).isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageMatching("String index out of range: -*\\d+");
    }

}

