package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ContentTypeTest {

    @DisplayName("Parse Content-Type")
    @ParameterizedTest
    @ValueSource(strings = {
            "text/html;charset=utf-8",
            "text/html;charset=UTF-8",
            "text/html; charset=utf-8",
            "TEXT/HTML;charset=utf-8"
    })
    void parse(String contentType) {
        assertThat(ContentType.parse(contentType)).isEqualTo(ContentType.TEXT_HTML_UTF_8);
    }

    @Test
    @DisplayName("If Content-Type not exists, should fail to parse")
    void parse_fail() {
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> ContentType.parse("INVALID/TEXT"));
    }
}