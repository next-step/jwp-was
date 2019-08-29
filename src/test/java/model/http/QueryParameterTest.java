package model.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryParameterTest {
    @ParameterizedTest
    @CsvSource({"userId=ssosso, userId, ssosso", "test_1=3939393, test_1, 3939393"})
    void of(String line, String key, String value) {
        QueryParameter actual = QueryParameter.of(line);
        QueryParameter expected = QueryParameter.of(key, value);

        assertThat(actual).isEqualTo(expected);
    }
}
