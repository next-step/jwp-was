package http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
class RequestLineTest {

    @Test
    @DisplayName("유효한 입력값으로 RequestLine을 생성 시에 정확하게 RequestLine이 생성되는지 테스트 - GET")
    void parseGETTest() {
        RequestLine requestLine = RequestLine.of("GET /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getQueryString()).isEqualTo(QueryString.ofNull());
        assertThat(requestLine.getProtocolVersion()).isEqualTo(new ProtocolVersion("HTTP/1.1"));
    }

    @Test
    @DisplayName("유효한 입력값으로 RequestLine을 생성 시에 정확하게 RequestLine이 생성되는지 테스트 - POST")
    void parsePOSTTest() {
        RequestLine requestLine = RequestLine.of("POST /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getQueryString()).isEqualTo(QueryString.ofNull());
        assertThat(requestLine.getProtocolVersion()).isEqualTo(new ProtocolVersion("HTTP/1.1"));
    }

    @Test
    @DisplayName("유효한 입력값으로 RequestLine을 생성 시에 정확하게 RequestLine이 생성되는지 테스트 - GET with QueryString")
    void parserequestWithQueryStringTest() {
        RequestLine requestLine = RequestLine.of("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getQueryString()).isEqualTo(QueryString.of("userId=javajigi&password=password&name=JaeSung"));
        assertThat(requestLine.getProtocolVersion()).isEqualTo(new ProtocolVersion("HTTP/1.1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "GET HTTP/1.1", "POST /users", "GET "})
    @DisplayName("유효하지 않은 입력값으로 RequestLine 을 생성 시에 지정한 예외가 발생하는지 테스트")
    void parseInvalidRequestTest(String requestInput) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            RequestLine.of(requestInput);
        }).withMessage(RequestLine.ILLEGAL_REQUEST_LINE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST /users HTTP/1.1", "NEXT /users HTTP/1.1"})
    @DisplayName("유효하지 않은 메서드로 RequestLine 을 생성 시에 지정한 예외가 발생하는지 테스트")
    void parseInvalidRequestMethodTest(String requestInput) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            RequestLine.of(requestInput);
        }).withMessage(String.format(HttpMethod.ILLEGAL_HTTP_METHOD, requestInput.split(" ")[0]));
    }
}
