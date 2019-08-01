package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpURLTest {

    @DisplayName("path를 구한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "/users",
            "/users?userId=javajigi&password=password&name=JaeSung"})
    void createPath_success(String path) {
        // when
        HttpURL httpURL = HttpURL.of(path);

        // then
        assertThat(httpURL.getPath()).isEqualTo("/users");
    }

    @DisplayName("query 속성의 값을 구한다")
    @ParameterizedTest
    @CsvSource({
            "'userId', 'javajigi'",
            "'password', 'password'",
            "'name', 'JaeSung'",
    })
    void returnQueryString_success(String attribute, String value) {
        // given
        String path = "/users?userId=javajigi&password=password&name=JaeSung";

        // when
        HttpURL httpURL = HttpURL.of(path);
        String result = httpURL.getRequestParam(attribute);

        // then
        assertThat(result).isEqualTo(value);
    }
}