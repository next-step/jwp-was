package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("리퀘스트 바디를 관리하는 클래스")
class RequestBodyTest {

//  userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
    @Test
    @DisplayName("리퀘스트 헤더들을 받으면 key, value로 나눠준다.")
    void parse() {
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        RequestBody requestBody = new RequestBody(body);

        assertThat(requestBody.getBodyParameter("userId")).isEqualTo("javajigi");
        assertThat(requestBody.getBodyParameter("password")).isEqualTo("password");
        assertThat(requestBody.getOrigin()).isEqualTo(body);
        assertThat(requestBody.getBodyParameter("not exist")).isNull();
    }
}