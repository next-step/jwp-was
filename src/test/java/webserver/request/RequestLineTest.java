package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("요청")
class RequestLineTest {

    @Test
    @DisplayName("문자열로 생성")
    void instance() {
        assertThatNoException().isThrownBy(() -> RequestLine.from("GET /users HTTP/1.1"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("문자열은 필수")
    void instance_nullOrEmpty_thrownIllegalArgumentException(String request) {
        assertThatIllegalArgumentException().isThrownBy(() -> RequestLine.from(request));
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET", "GET /URI", "HTTP"})
    @DisplayName("반드시 Method SP Request-URI SP HTTP-Version 의 패턴")
    void instance_invalidPattern_thrownIllegalArgumentException(String request) {
        assertThatIllegalArgumentException().isThrownBy(() -> RequestLine.from(request));
    }

    @Test
    @DisplayName("GET 요청 분석")
    void parseGetRequest() {
        //given
        RequestLine request = RequestLine.from("GET /users HTTP/1.1");
        //when, then
        assertAll(
                () -> assertThat(request.method()).isEqualTo(HttpMethod.GET),
                () -> assertThat(request.path()).isEqualTo(Path.from("/users")),
                () -> assertThat(request.protocolName()).isEqualTo("HTTP"),
                () -> assertThat(request.protocolVersion()).isEqualTo("1.1")
        );
    }

    @Test
    @DisplayName("POST 요청 분석")
    void parsePostRequest() {
        //given
        RequestLine request = RequestLine.from("POST /users HTTP/1.1");
        //when, then
        assertAll(
                () -> assertThat(request.method()).isEqualTo(HttpMethod.POST),
                () -> assertThat(request.path()).isEqualTo(Path.from("/users")),
                () -> assertThat(request.protocolName()).isEqualTo("HTTP"),
                () -> assertThat(request.protocolVersion()).isEqualTo("1.1")
        );
    }

    @Test
    @DisplayName("Query String이 포함된 분석")
    void parseGetRequestWithQueryString() {
        //given
        RequestLine request = RequestLine.from("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        //when, then
        assertAll(
                () -> assertThat(request.method()).isEqualTo(HttpMethod.GET),
                () -> assertThat(request.path()).isEqualTo(Path.from("/users")),
                () -> assertThat(request.protocolName()).isEqualTo("HTTP"),
                () -> assertThat(request.protocolVersion()).isEqualTo("1.1"),
                () -> assertThat(request.query()).isEqualTo(QueryString.from("userId=javajigi&password=password&name=JaeSung"))
        );
    }
}
