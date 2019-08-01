package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class QueryStringTest {

    @DisplayName("queryString을 생성한다")
    @Test
    void createQueryString_success() {
        // given
        String path = "userId=javajigi&password=password&name=JaeSung";

        // when
        QueryString queryString = QueryString.of(path);
        Map<String, String> parameters = queryString.getParameters();

        // then
        assertThat(parameters.size()).isEqualTo(3);
    }

    @DisplayName("해당 속성의 value를 추출하는데 성공한다")
    @ParameterizedTest
    @CsvSource({
            "'userId', 'javajigi'",
            "'password', 'password'",
            "'name', 'JaeSung'",

    })
    void getValue_success(String attribute, String value) {
        // given
        String path = "userId=javajigi&password=password&name=JaeSung";

        // when
        QueryString queryString = QueryString.of(path);

        // then
        assertThat(queryString.get(attribute)).isEqualTo(value);
    }
}