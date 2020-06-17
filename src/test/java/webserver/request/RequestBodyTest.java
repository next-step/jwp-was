package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {

    @DisplayName("request body 파싱")
    @Test
    void of() {

        // given
        String data = "userId=test&password=test&name=test&email=test@test";

        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("userId", "test");
        requestBodyMap.put("password", "test");
        requestBodyMap.put("name", "test");
        requestBodyMap.put("email", "test@test");

        // when
        RequestBody requestBody = RequestBody.of(data);

        // then
        assertThat(requestBody)
                .isEqualTo(new RequestBody(requestBodyMap));
    }

    @DisplayName("request body의 값 확인")
    @Test
    void get() {

        // given
        String data = "userId=test&password=test&name=test&email=test@test";

        // when
        RequestBody requestBody = RequestBody.of(data);

        // then
        assertThat(requestBody.get("userId"))
                .isEqualTo("test");
    }
}