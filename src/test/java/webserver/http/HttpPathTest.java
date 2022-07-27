package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpPathTest {

    @DisplayName("문자열을 받아 HttpPath를 생성한다")
    @ParameterizedTest(name = "\"{0}\" HttpPath 생성")
    @CsvSource({"/hello", "/", "/test123"})
    void create(String path) {
        assertThat(new HttpPath(path)).isNotNull();
    }

    @DisplayName("잘못된 형식의 문자열의 경우 Exception이 발생한다")
    @ParameterizedTest(name = "\"{0}\" HttpPath 생성")
    @CsvSource({"hello", "%test"})
    void invalidStr(String path) {
        assertThatThrownBy(() -> new HttpPath(path))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Querystring RequestLine 파싱")
    @Test
    void querystring() {
        HttpPath httpPath = new HttpPath("/hello?key=value");
        assertThat(httpPath.getHttpParams()).isNotNull();
    }
}
