package http;

import http.parsers.RequestParser;
import http.requests.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: remove me
@DisplayName("사용자의 요청을 Request Context 객체로 파싱하는 모듈을 테스트한다")
public class RequestParserTest {

    @DisplayName("요청을 파싱하면 요청 객체가 반환된다.")
    @Test
    void parse_request() {
        final String rawRequestLine = "GET /index.html HTTP/1.1";
        final List<String> rawRequestHeaders = new ArrayList<>();
        rawRequestHeaders.add("Host: localhost:8080");
        rawRequestHeaders.add("Connection: keep-alive");
        rawRequestHeaders.add("Cache-Control: no-cache");
        final HttpRequest httpRequest = RequestParser.parse(rawRequestLine, rawRequestHeaders);
        assertThat(httpRequest).isNotNull();
    }
}
