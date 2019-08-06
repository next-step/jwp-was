package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.HttpHeaders;
import webserver.request.QueryString.Parameter;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class QueryStringTest {

    @DisplayName("queryString 을 생성하는데 성공한다")
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

    @DisplayName("queryString 이 없을 시 빈 값을 반환한다")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void createQueryString_inputNull_success(String empty) {
        // when
        QueryString queryString = QueryString.of(empty);
        Map<String, String> parameters = queryString.getParameters();

        // then
        assertThat(parameters.isEmpty()).isTrue();
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
        String queryString = "userId=javajigi&password=password&name=JaeSung";

        // when
        QueryString result = QueryString.of(queryString);

        // then
        assertThat(result.get(attribute)).isEqualTo(value);
    }

    @DisplayName("파라미터가 null or empty일 경우 생성에 실패한다")
    @ParameterizedTest
    @NullAndEmptySource
    void ofParameter_whenInputNull_exception(String wrong) {
        // exception
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Parameter.of(wrong));
    }
}