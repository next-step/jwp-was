package http;

import http.request.RequestParams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestParamsTest {
    static Stream<String> blankStrings() {
        return Stream.of("", null);
    }

    @Test
    @DisplayName("쿼리 스트링을 입력하고 값을 가지고 온다")
    void getParameter() {
        final RequestParams requestParams = RequestParams.from("userId=jinwoo&password=1q2w3e4r");
        assertThat(requestParams.getParameter("userId")).isEqualTo("jinwoo");
        assertThat(requestParams.getParameter("password")).isEqualTo("1q2w3e4r");
    }

    @ParameterizedTest
    @DisplayName("빈 쿼리 스트링 입력")
    @MethodSource("blankStrings")
    void createEmptyQueryString(final String value) {
        final RequestParams requestParams = RequestParams.from(value);
        assertThat(requestParams).isNotNull();
    }
}
