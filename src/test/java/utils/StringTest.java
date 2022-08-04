package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("String 테스트")
public class StringTest {

    @DisplayName("substring 테스트")
    @Test
    void substringTest() {
        String s = "/index.html";
        String extension = s.substring(s.lastIndexOf('.') + 1);
        assertThat(extension).isEqualTo("html");
    }
}
