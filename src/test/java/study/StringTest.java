package study;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class StringTest {
    @Test
    void split() {
        String[] result = "1,2".split(",");
        assertThat(result).contains("2");
        assertThat(result).containsExactly("1", "2");
    }

    @Test
    void substring() {
        String result = "(1,2)".substring(1,4);
        assertThat(result).contains("1,2");
    }

    @Test
    @DisplayName("StringIndexOutOfBoundsException test")
    void charAt() {
//        assertThatThrownBy(() -> {
//            char result = "abc".charAt(3);
//        }).isInstanceOf(StringIndexOutOfBoundsException.class)
//                .hasMessageContaining("Index: 3, Size: 3");

        assertThatExceptionOfType(StringIndexOutOfBoundsException.class)
                .isThrownBy(() -> {
                    char result = "abc".charAt(3);
                }).withMessageMatching("Index: \\d+, Size: \\d+");
    }
}
