package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.domain.RequestBody;
import webserver.http.domain.RequestParams;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static webserver.http.RequestTestConstant.INVALID_QUERY_STRING;
import static webserver.http.RequestTestConstant.QUERY_STRING;

public class RequestBodyTest {
    @Test
    @DisplayName("Request Body 정보를 가져온다.")
    void requestBody() {
        // when
        RequestBody requestBody = new RequestBody("userId=test&password=password&name=testName&email=test@test.com");

        // then
        Map<String, String> bodies = requestBody.bodies();
        assertThat(bodies).isNotEmpty();
        assertThat(bodies.get("userId")).isEqualTo("test");
        assertThat(bodies.get("password")).isEqualTo("password");
        assertThat(bodies.get("name")).isEqualTo("testName");
        assertThat(bodies.get("email")).isEqualTo("test@test.com");

    }

    @Test
    @DisplayName("요청 본문의 형태가 key=value의 형태가 아닐 경우 Exception을 발생 시킨다.")
    void requestParamInvalidFormatThrowException() {
        // then
        assertThatThrownBy(
                () -> new RequestBody("userId-test&password-password&name-testName")
        ).isExactlyInstanceOf(IllegalArgumentException.class);

    }
}
