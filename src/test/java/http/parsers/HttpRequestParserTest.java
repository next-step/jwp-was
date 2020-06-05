package http.parsers;

import http.requests.HttpRequest;
import http.types.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("요청 컨텍스트를 파싱하는 클래스 테스트")
class HttpRequestParserTest {

    // =_= 수제(요)청. 아 라임청에이드 먹고싶다
    final String testRequest =
            "POST /user/create HTTP/1.1\r\n" +
                    "Host: localhost:8080\r\n" +
                    "Content-Length: 76\r\n" +
                    "Content-Type: application/x-www-form-urlencoded\r\n" +
                    "\r\n" +
                    "userId=hyeyoom&password=1234abcd&name=Chiho+Won&email=neoul_chw%40icloud.com";

    @DisplayName("바디까지 포함하여 파싱했는지 테스트!")
    @Test
    void test_request_including_body() throws Exception {
        try (final InputStream input = new ByteArrayInputStream(testRequest.getBytes())) {
            final HttpRequest ctx = RequestContextParser.parse(input);
            assertThat(ctx.getMethod()).isEqualTo(HttpMethod.POST);
            assertThat(ctx.getPath()).isEqualTo("/user/create");
            assertThat(ctx.getBody()).isEqualTo("userId=hyeyoom&password=1234abcd&name=Chiho+Won&email=neoul_chw%40icloud.com");
        }
    }
}