package webserver.http;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryParamTest {

    private final String queryString = "userId=javajigi&password=password&name=JaeSung";

    @ParameterizedTest
    @CsvSource(value = {"userId=javajigi", "password=password", "name=JaeSung"}, delimiter = '=')
    void parse(String key, String value) {
        QueryParam queryParam = QueryParam.parse(queryString);
        assertThat(queryParam.get(key).isPresent()).isTrue();
        assertThat(queryParam.get(key).orElseThrow(IllegalArgumentException::new)).isEqualTo(value);
    }

    @Test
    void parse_빈문자열_입력() {
        String queryString = "";
        QueryParam queryParam = QueryParam.parse(queryString);
        assertThat(queryParam).isSameAs(QueryParam.EMTPY);
    }

    @Test
    void paramKeys() {
        Set<String> expectedKeys = ImmutableSet.of("userId", "password", "name");

        QueryParam queryParam = QueryParam.parse(queryString);

        assertThat(queryParam.paramKeys()).isEqualTo(expectedKeys);
    }

    @Test
    void paramValues() {
        List<String> expectedValues = ImmutableList.of("javajigi", "password", "JaeSung");

        QueryParam queryParam = QueryParam.parse(queryString);

        assertThat(queryParam.paramValues()).containsAll(expectedValues);
    }
}
