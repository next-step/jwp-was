package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.domain.RequestBody;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RequestBodyTest {
    @Test
    @DisplayName("Request Body 정보를 가져온다.")
    void requestBody() {
        // when
        RequestBody requestBody = new RequestBody("userId=test&password=password&name=testName&email=test@test.com");

        // then
        assertThat(requestBody.body("userId")).isEqualTo("test");
        assertThat(requestBody.body("password")).isEqualTo("password");
        assertThat(requestBody.body("name")).isEqualTo("testName");
        assertThat(requestBody.body("email")).isEqualTo("test@test.com");
    }
}
