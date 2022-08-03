package webserver.http.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RequestLineTest {

    @Test
    void GET_요청_파싱() {
        RequestLine requestLine = RequestLine.from("GET /path HTTP/2");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void POST_요청_파싱() {
        RequestLine requestLine = RequestLine.from("POST /path HTTP/2");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
    }

    @Test
    void 잘못된_형식_요청() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> RequestLine.from(""))
                .withMessage("형식에 맞지 않는 요청입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET /path HTTP/2", "GET /path? HTTP/2", "GET /path?key=value HTTP/2"})
    void 경로_파싱(String request) {
        RequestLine requestLine = RequestLine.from(request);

        Path path = requestLine.getPath();

        assertThat(path.getDepth()).isEqualTo(1);
        assertThat(path.getSubPathByDepth(0).get()).isEqualTo("path");
    }

    @Test
    void 쿼리스트링_파싱() {
        RequestLine requestLine = RequestLine.from("GET /path?key=value&id=test HTTP/2");

        Parameters parameters = requestLine.getPath().getQueries();

        assertThat(parameters.get("key")).isEqualTo("value");
        assertThat(parameters.get("id")).isEqualTo("test");
    }

    @Test
    void 프로토콜_파싱() {
        RequestLine requestLine = RequestLine.from("GET /path HTTP/2");

        assertThat(requestLine.getProtocol()).isEqualTo(new Protocol("HTTP", new Version("2")));
    }
}
