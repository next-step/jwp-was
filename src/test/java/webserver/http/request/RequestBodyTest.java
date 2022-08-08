package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {

    @DisplayName("RequestBody 가 들어왔을 때")
    @Test
    void getRequestBody() {
        assertThat(new RequestBody("userId=aaaa&password=aaaa&name=aaaa&email=aaaa@naver.com")).isEqualTo(new RequestBody("userId", "aaaa", "password", "aaaa", "name", "aaaa", "email", "aaaa@naver.com"));
    }
}