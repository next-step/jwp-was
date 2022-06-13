package utils.http;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpQueryStringParserTest {

    @Test
    void parse() {
        // given
        String queryString = "userId=javajigi&password=password&name=JaeSung";

        // when
        Map<String, String> queryParams = HttpQueryStringParser.parse(queryString);

        // then
        assertThat(queryParams).isNotEmpty();
        assertThat(queryParams).containsEntry("userId", "javajigi");
        assertThat(queryParams).containsEntry("password", "password");
        assertThat(queryParams).containsEntry("name", "JaeSung");
    }

    @ParameterizedTest
    @MethodSource
    void parseWithEmpty(String queryString) {
        // when
        Map<String, String> queryParams = HttpQueryStringParser.parse(queryString);

        // then
        assertThat(queryParams).isEmpty();
    }

    private static Stream<String> parseWithEmpty() {
        return Stream.of(
                null,
                ""
        );
    }
}
