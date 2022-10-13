package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.Method;
import webserver.http.Type;
import webserver.http.Version;
import webserver.http.request.RequestLine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayName("HTTP 요청 파싱 테스트")
class RequestLineTest {

    @ParameterizedTest
    @ValueSource(strings = {"GET /users HTTP/1.1", "GET /index.html HTTP/1.1"})
    @DisplayName("Get 요청을 파싱한다.")
    void parseGetRequest(String input) {
        final RequestLine actual = RequestLine.parse(input);

        assertThat(actual.getMethod()).isEqualTo(Method.GET);
        assertThat(actual.getProtocolType()).isEqualTo(Type.HTTP);
        assertThat(actual.getProtocolVersion()).isEqualTo(Version.VERSION1_1);
        assertThat(actual.getPathValue()).containsAnyOf("/users", "/index.html");
    }

    @Test
    @DisplayName("Get 요청과 QeuryString을 파싱한다.")
    void parseGetRequestWithQueryString() {
        final String input = "GET /user/create?userId=javajigi&password=password&name=JaeSung&email=javajigi@slipp.net HTTP/1.1";

        final RequestLine actual = RequestLine.parse(input);

        assertThat(actual.getMethod()).isEqualTo(Method.GET);
        assertThat(actual.getProtocolType()).isEqualTo(Type.HTTP);
        assertThat(actual.getProtocolVersion()).isEqualTo(Version.VERSION1_1);
        assertThat(actual.getPathValue()).isEqualTo("/user/create");

        assertSoftly(softAssertions -> {
                    softAssertions.assertThat(actual.getParameter("userId")).isEqualTo("javajigi");
                    softAssertions.assertThat(actual.getParameter("password")).isEqualTo("password");
                    softAssertions.assertThat(actual.getParameter("name")).isEqualTo("JaeSung");
                    softAssertions.assertThat(actual.getParameter("email")).isEqualTo("javajigi@slipp.net");
                }
        );
    }

    @Test
    @DisplayName("Post 요청을 파싱한다.")
    void parsePostRequest() {
        final String input = "POST /users HTTP/1.1";

        final RequestLine actual = RequestLine.parse(input);

        assertThat(actual.getMethod()).isEqualTo(Method.POST);
        assertThat(actual.getProtocolType()).isEqualTo(Type.HTTP);
        assertThat(actual.getProtocolVersion()).isEqualTo(Version.VERSION1_1);
        assertThat(actual.getPathValue()).isEqualTo("/users");
    }
}
