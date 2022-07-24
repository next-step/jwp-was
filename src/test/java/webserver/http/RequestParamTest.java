package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static webserver.http.RequestTestConstant.INVALID_QUERY_STRING;
import static webserver.http.RequestTestConstant.QUERY_STRING;

public class RequestParamTest {
    @Test
    @DisplayName("파라미터를 가져온다.")
    void requestParam() {
        // when
        RequestParams requestParams = new RequestParams(QUERY_STRING);

        // then
        Map<String, String> params = requestParams.params();
        assertThat(params).isNotEmpty();
        assertThat(params.get("userId")).isEqualTo("test");
        assertThat(params.get("password")).isEqualTo("password");
        assertThat(params.get("name")).isEqualTo("test");

    }

    @Test
    @DisplayName("파라미터의 형태가 key=value의 형태가 아닐 경우 Exception을 발생 시킨다.")
    void requestParamInvalidFormatThrowException() {
        // then
        assertThatThrownBy(
                () -> new RequestParams(INVALID_QUERY_STRING)
        ).isExactlyInstanceOf(IllegalArgumentException.class);

    }
}
