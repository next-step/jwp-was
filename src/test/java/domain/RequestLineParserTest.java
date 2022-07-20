package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("RequestLineParser 단위 테스트")
public class RequestLineParserTest {
    @DisplayName("GET RequestLine을 파싱하여 HttpRequest를 얻는다.")
    @Test
    void parseForGet() {
        // given
        RequestLineParser requestLineParser = new RequestLineParser();

        // when
        HttpRequest httpRequest = requestLineParser.parse("GET /users HTTP/1.1");

        // then
        assertThat(httpRequest).isEqualTo(
                new HttpRequest(HttpMethod.GET, new HttpPath("/users"), new HttpProtocol("HTTP/1.1")));
    }

    @DisplayName("POST RequestLine을 파싱하여 HttpRequest를 얻는다.")
    @Test
    void parseForPost() {
        // given
        RequestLineParser requestLineParser = new RequestLineParser();

        // when
        HttpRequest httpRequest = requestLineParser.parse("POST /users HTTP/1.1");

        // then
        assertThat(httpRequest).isEqualTo(
                new HttpRequest(HttpMethod.POST, new HttpPath("/users"), new HttpProtocol("HTTP/1.1")));
    }

    @DisplayName("Query String이 있는 RequestLine을 파싱하여 HttpRequest를 얻는다.")
    @Test
    void parseWithQueryString() {
        // given
        RequestLineParser requestLineParser = new RequestLineParser();

        // when
        HttpRequest httpRequest = requestLineParser.parse("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        // then
        assertThat(httpRequest).isEqualTo(
                new HttpRequest(HttpMethod.GET, new HttpPath("/users?userId=javajigi&password=password&name=JaeSung"), new HttpProtocol("HTTP/1.1")));
    }
}
