package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class StringTest {

    @Test
    void twoNumberSplitTest() {
        final String data = "1,2";
        final String[] expected = {"1", "2"};

        final String[] result = data.split(",");

        assertThat(result).containsExactly(expected);
    }

    @Test
    void oneNumberSplitTest() {
        final String data = "1";
        final String expected = "1";

        final String[] result = data.split(",");

        assertThat(result).containsExactly(expected);
    }

    @Test
    void substringTest() {
        final String data = "(1,2)";
        final String expected = "1,2";

        final String result = data.substring(data.indexOf('(') + 1, data.lastIndexOf(')'));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("String.substring() 사용시 시작 인덱스가 0 보다  작 경우 에러가 발생한다")
    void substringStringIndexOutOfBoundsExceptionTest() {
        final String data = "Hi";
        final int beginIndex = -1;

        final Throwable thrown = catchThrowable(() -> data.substring(beginIndex));

        assertThat(thrown)
                .isInstanceOf(StringIndexOutOfBoundsException.class)
                .hasMessageContaining("String index out of range: " + beginIndex);
    }

    @Test
    @DisplayName("String.substring() 사용시 시작 인덱스가 문자열 길이보다 큰 경우 에러가 발생한다")
    void substringStringIndexOutOfBoundsExceptionTest2() {
        final String data = "Hi";
        final int beginIndex = 3;

        final Throwable thrown = catchThrowable(() -> data.substring(beginIndex));

        assertThat(thrown)
                .isInstanceOf(StringIndexOutOfBoundsException.class)
                .hasMessageContaining("String index out of range: " + (data.length() - beginIndex));
    }

}
