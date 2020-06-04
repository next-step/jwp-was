package http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class RequestLineParserTest {

    @Test
    void parseGETTest() {
        RequestLine requestLine = RequestLineParser.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getHttpMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocolVersion()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void parsePOSTTest() {
        RequestLine requestLine = RequestLineParser.parse("POST /users HTTP/1.1");
        assertThat(requestLine.getHttpMethod()).isEqualTo("POST");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocolVersion()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void parserequestWithQueryStringTest() {
        RequestLine requestLine = RequestLineParser.parse("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        assertThat(requestLine.getHttpMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getQueryString()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
        assertThat(requestLine.getProtocolVersion()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "GET HTTP/1.1", "POST /users", "GET "})
    void parseInvalidRequestTest(String requestInput) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            RequestLineParser.parse(requestInput);
        }).withMessage(RequestLineParser.ILLEGAL_REQUEST_LINE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST /users HTTP/1.1", "NEXT /users HTTP/1.1"})
    void parseInvalidRequestMethodTest(String requestInput) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            RequestLineParser.parse(requestInput);
        }).withMessage(HttpMethod.ILLEGAL_HTTP_METHOD);
    }
}
