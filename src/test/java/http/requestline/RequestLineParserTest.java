package http.requestline;

import http.requestline.exception.IllegalRequestLineParsingException;
import http.requestline.protocol.Protocol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestLineParserTest {

    @DisplayName("GET 요청 Request Line Parsing 하기")
    @Test
    void parseGetRequest() {
        /* given */
        String request = "GET /users HTTP/1.1";

        /* when */
        RequestLine requestLine = RequestLineParser.parse(request);

        /* then */
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo(Protocol.HTTP);
        assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1");
    }

    @DisplayName("POST 요청 Request Line Parsing 하기")
    @Test
    void parsePostRequest() {
        /* given */
        String request = "POST /users HTTP/1.1";

        /* when */
        RequestLine requestLine = RequestLineParser.parse(request);

        /* then */
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo(Protocol.HTTP);
        assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1");
    }

    @DisplayName("파라미터 null 체크하기")
    @Test
    void checkNull() {
        /* when */ /* then */
        assertThrows(IllegalRequestLineParsingException.class, () -> RequestLineParser.parse(null));
    }

    @DisplayName("RequestLine를 공백 기준으로 나눴을 때 세 파트가 나오지 않으면 IllegalRequestLineParsingException")
    @ParameterizedTest
    @ValueSource(strings = {"GET /users", "GET", "GET /users HTTP/1.1 temp"})
    void parse_IllegalArgumentException(String request) { /* given */
        /* when */ /* then */
        assertThrows(IllegalRequestLineParsingException.class, () -> RequestLineParser.parse(request));
    }
}
