package handler;

import static org.assertj.core.api.Assertions.assertThat;

import http.Method;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HandlerMatcherTest {

    @ParameterizedTest
    @CsvSource({
        "\\/.*\\.html,/index.html, true",
        "\\/.*\\.html,/path/index.html, true",
        "\\/.*\\.html,/path/indexhtml, false",
        "\\/css\\/.*,/css/test, true",
        "\\/css\\/.*,/css/index.html, true",
        "\\/css\\/.*,/css, false",
    })
    void path_pattern(String pattern, String path, boolean result) {
        HandlerMatcher handlerMatcher = new HandlerMatcher(Method.GET, pattern);
        assertThat(handlerMatcher.isMatch(Method.GET, path)).isEqualTo(result);
    }
}
