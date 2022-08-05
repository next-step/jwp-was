package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HttpRequestParser 단위 테스트")
class HttpRequestParserTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    @DisplayName("GET HTTP 요청을 생성한다.")
    @Test
    void parseGet() throws FileNotFoundException {
        // given
        InputStream in = new FileInputStream(TEST_DIRECTORY + "httpGet.txt");

        // when
        final HttpRequest httpRequest = HttpRequestParser.parse(in);

        // then
        final RequestLine requestLine = httpRequest.getRequestLine();
        assertAll(
                () -> assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(requestLine.getPath()).isEqualTo("/user/create"),
                () -> assertThat(requestLine.getQueryString("userId")).isEqualTo("javajigi"),
                () -> assertThat(requestLine.getHttpProtocol()).isEqualTo(new HttpProtocol("HTTP/1.1")),
                () -> assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(httpRequest.getAttributes()).isEmpty()
        );
    }

    @DisplayName("POST HTTP 요청을 생성한다.")
    @Test
    void parsePost() throws FileNotFoundException {
        // given
        InputStream in = new FileInputStream(TEST_DIRECTORY + "httpPost.txt");

        // when
        final HttpRequest httpRequest = HttpRequestParser.parse(in);

        // then
        assertAll(
                () -> assertThat(httpRequest.getRequestLine()).isEqualTo(
                        new RequestLine(
                                HttpMethod.POST,
                                new HttpPath("/user/create"),
                                new HttpProtocol("HTTP/1.1")
                        )
                ),
                () -> assertThat(httpRequest.getContentLength()).isEqualTo(46),
                () -> assertThat(httpRequest.getAttributes()).isEqualTo(Map.of("userId", "javajigi", "password", "password", "name", "JaeSung"))
        );
    }

    @DisplayName("쿼리스트링을 포함한 POST HTTP 요청을 생성한다.")
    @Test
    void parsePost2() throws FileNotFoundException {
        // given
        InputStream in = new FileInputStream(TEST_DIRECTORY + "httpPost2.txt");

        // when
        final HttpRequest httpRequest = HttpRequestParser.parse(in);

        // then
        final RequestLine requestLine = httpRequest.getRequestLine();
        assertAll(
                () -> assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.POST),
                () -> assertThat(requestLine.getPath()).isEqualTo("/user/create"),
                () -> assertThat(requestLine.getQueryString("id")).isEqualTo("1"),
                () -> assertThat(requestLine.getHttpProtocol()).isEqualTo(new HttpProtocol("HTTP/1.1")),
                () -> assertThat(httpRequest.getContentLength()).isEqualTo(46),
                () -> assertThat(httpRequest.getAttributes()).isEqualTo(Map.of("userId", "javajigi", "password", "password", "name", "JaeSung"))
        );
    }
}
