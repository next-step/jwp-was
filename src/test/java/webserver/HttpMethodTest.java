package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class HttpMethodTest {

    @DisplayName("Http Method 반환")
    @Test
    void resolve() {

        // given
        List<String> httpMethodTexts = Arrays.asList("GET", "POST");

        // when
        List<HttpMethod> httpMethods = httpMethodTexts.stream()
                .map(HttpMethod::resolve)
                .collect(toList());

        // then
        assertThat(httpMethods).isEqualTo(Arrays.asList(HttpMethod.GET, HttpMethod.POST));
    }

    @DisplayName("Http Method 반환 - 파라미터가 null인 경우")
    @Test
    void resolve_null() {

        // given
        String httpMethodText = null;

        // when
        HttpMethod httpMethod = HttpMethod.resolve(httpMethodText);

        // then
        assertThat(httpMethod).isNull();
    }

    @DisplayName("Http Method 반환 - 일치하는 Http method가 없는 경우")
    @Test
    void resolve_mismatch() {

        // given
        String httpMethodText = "NOT_HTTP_METHOD";

        // when
        HttpMethod httpMethod = HttpMethod.resolve(httpMethodText);

        // then
        assertThat(httpMethod).isNull();
    }
}