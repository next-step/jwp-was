package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMessageTest {

    @DisplayName("HTTP 요청은 requestLine과 header로 파싱되어 RequestMessage로 생성된다")
    @Test
    void test_createRequestMessage_should_pass() throws IOException {
        // given
        String input = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "\n";
        StringReader sr = new StringReader(input);
        BufferedReader br = new BufferedReader(sr);
        // when
        RequestMessage requestMessage = RequestMessage.from(br);
        // then
        assertThat(requestMessage.equals(
                RequestMessage.of(RequestLine.from("GET /index.html HTTP/1.1"),
                        new Header(Arrays.asList("Host: localhost:8080", "Connection: keep-alive", "Accept: */*")))))
                .isTrue();
    }

}