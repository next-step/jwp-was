package model.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryParameterTest {
    @ParameterizedTest
    @ValueSource(strings = {"userId=ssosso", "test_1=3939393"})
    void of(String parameter) {
        QueryParameter actual = QueryParameter.of(parameter);
        QueryParameter expected = QueryParameter.of(parameter.split("=")[0], parameter.split("=")[1]);

        assertThat(actual).isEqualTo(expected);
    }
}
