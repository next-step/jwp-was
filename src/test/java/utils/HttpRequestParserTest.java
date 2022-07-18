package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.http.HttpMethod;
import utils.http.HttpRequest;
import utils.http.parser.HttpRequestParser;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;


class HttpRequestParserTest {

    @Test
    @DisplayName("Get 요청을 정상적으로 parsing 하는지 테스트")
    void getRequestParsingTest() {
        // given
        String request = "GET /users HTTP/1.1";
        HttpRequest expected = new HttpRequest(HttpMethod.GET, "/users", "HTTP", "1.1", Collections.emptyMap());

        // when
        HttpRequest httpRequest = HttpRequestParser.parse(request);

        // then
        System.out.println(httpRequest);
        assertThat(httpRequest).isEqualTo(expected);
    }

    @Test
    @DisplayName("Post 요청을 정상적으로 parsing 하는지 테스트")
    void postRequestParsingTest() {
        // given
        String request = "POST /users HTTP/1.1";
        HttpRequest expected = new HttpRequest(HttpMethod.POST, "/users", "HTTP", "1.1", Collections.emptyMap());

        // when
        HttpRequest httpRequest = HttpRequestParser.parse(request);

        // then
        System.out.println(httpRequest);
        assertThat(httpRequest).isEqualTo(expected);
    }

    @Test
    @DisplayName("QueryString을 정상적으로 parsing 하는지 테스트")
    void queryStringParsingTest() {
        // given
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        HttpRequest expected = new HttpRequest(
                HttpMethod.GET,
                "/users",
                "HTTP",
                "1.1",
                Map.of("userId", "javajigi", "password", "password", "name", "JaeSung")
        );

        // when
        HttpRequest httpRequest = HttpRequestParser.parse(request);

        // then
        System.out.println(httpRequest);
        assertThat(httpRequest).isEqualTo(expected);
    }

    @Test
    @DisplayName("HTTP REQUEST 양식에 맞지 않는 값을 요청했을때 IllegalArgumentException이 나오는지 테스트")
    void exceptionTest() {
        // given
        String request = "GET /usersHTTP/1.1";

        // then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> HttpRequestParser.parse(request))
                .withMessage("HTTP REQUEST 형식에 맞지 않습니다. request: " + request);
    }
}