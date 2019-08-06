/*
package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Response;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @DisplayName("Body가 존재하는 경우 응답을 생성하는데 성공한다")
    @Test
    void of_includeBody_success() {
        // given
        HttpStatus success = HttpStatus.SUCCESS;
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        // when
        Response response = ResponseFactory.ok(body);

        // then
        assertThat(response).isNotNull();
    }
}*/
