package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseBodyTest {

    @DisplayName("request body 생성확인 - 정적파일 요청이 아닌 경우")
    @Test
    void of() throws IOException, URISyntaxException {

        // given
        RequestLine requestLine = RequestLine.of("GET /users HTTP/1.1");

        // when
        ResponseBody responseBody = ResponseBody.of(requestLine);

        // then
        assertThat(responseBody).isEqualTo(new ResponseBody());
    }
}