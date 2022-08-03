package study;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {

    @Test
    void 두_문자열_matches() {
        String s = "/123";
        String s2 = "/.*";

        assertThat(s.matches(s2)).isTrue();
    }
}
