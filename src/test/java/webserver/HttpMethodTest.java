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
}