package model;

import http.parsers.RequestContextParser;
import http.parsers.RequestParser;
import http.requests.RequestContext;
import http.types.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("사용자 파싱하는 클래스 테스트")
class UserParserTest {

    @DisplayName("POST 요청을 통해 생성된 user 모델과 같은 조건으로 생성한 user 모델이 동일한지 테스트")
    @Test
    void post_test_that_model_is_created() throws Exception {
        final String testRequest =
                "POST /user/create HTTP/1.1\r\n" +
                        "Host: localhost:8080\r\n" +
                        "Content-Length: 76\r\n" +
                        "Content-Type: application/x-www-form-urlencoded\r\n" +
                        "\r\n" +
                        "userId=hyeyoom&password=1234abcd&name=Chiho+Won&email=neoul_chw%40icloud.com";

        try (final InputStream input = new ByteArrayInputStream(testRequest.getBytes())) {
            final RequestContext ctx = RequestContextParser.parse(input);
            final User userFromRequest = UserParser.parse(ctx);
            final User userFromManual = new User("hyeyoom", "1234abcd", "Chiho Won", "neoul_chw@icloud.com");
            // 공장 생산 사용자와 수제 생산 사용자가 같으면 성공!
            assertThat(userFromRequest).isEqualTo(userFromManual);
        }
    }
}