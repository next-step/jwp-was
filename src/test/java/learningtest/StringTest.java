package learningtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StringTest {

    @DisplayName("','를 기준으로 split한다.")
    @Test
    void split() {
        /* given */
        String sampleText = "1,2";

        /* when */
        String[] tokens = sampleText.split(",");

        /* then */
        assertThat(tokens).containsExactly("1", "2");
    }

    @DisplayName("','가 없는 문자열을 split한 경우 그대로 나온다.")
    @Test
    void split2() {
        /* given */
        String sampleText = "1";

        /* when */
        String[] tokens = sampleText.split(",");

        /* then */
        assertThat(tokens).containsExactly("1");
    }

    @DisplayName("substring()으로 괄호 안의 문자열을 추출한다.")
    @Test
    void substring() {
        /* given */
        String sampleText = "(1,2)";

        /* when */
        String extractedText = sampleText.substring(1, sampleText.length() - 1);

        /* then */
        assertThat(extractedText).isEqualTo("1,2");
    }

    @DisplayName("charAt()으로 특정 위치의 문자 가져오기")
    @Test
    void charAt() {
        /* given */
        String sampleText = "abc";

        /* when */
        char c1 = sampleText.charAt(0);
        char c2 = sampleText.charAt(1);
        char c3 = sampleText.charAt(2);

        /* then */
        assertThat(c1).isEqualTo('a');
        assertThat(c2).isEqualTo('b');
        assertThat(c3).isEqualTo('c');
    }

    @DisplayName("잘못된 index로 char 문자를 지정한 경우 StringIndexOutOfBoundsException")
    @Test
    void charAt_StringIndexOutOfBoundsException() {
        /* when */ /* then */
        assertThatThrownBy(() -> "abc".charAt(4))
                .isInstanceOf(StringIndexOutOfBoundsException.class);
    }
}
