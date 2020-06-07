package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {
    static Stream<String> blankStrings() {
        return Stream.of("", null);
    }

    @Test
    @DisplayName("쿼리 스트링을 입력하고 값을 가지고 온다")
    void getParameter() {
        QueryString queryString = QueryString.of("userId=jinwoo&password=1q2w3e4r");
        assertThat(queryString.getParameter("userId")).isEqualTo("jinwoo");
        assertThat(queryString.getParameter("password")).isEqualTo("1q2w3e4r");
    }

    @ParameterizedTest
    @DisplayName("빈 쿼리 스트링 입력")
    @MethodSource("blankStrings")
    void createEmptyQueryString(final String value) {
        QueryString queryString = QueryString.of(value);
        assertThat(queryString).isNotNull();
    }
}
