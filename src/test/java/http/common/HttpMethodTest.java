package http.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class HttpMethodTest {
    private static final Logger log = LoggerFactory.getLogger(HttpMethodTest.class);

    @DisplayName("resolve(빈 문자열 및 null) 테스트")
    @ParameterizedTest
    @NullAndEmptySource
    public void testResolveForNullAndEmpty(String method) {
        assertThat(HttpMethod.resolve(method)).isNull();
    }

    @DisplayName("resolve 테스트")
    @ParameterizedTest
    @ValueSource(strings = {
            "GET",
            "POST",
    })
    public void testResolve(String method) {
        assertThat(HttpMethod.resolve(method)).isNotNull();
    }

}