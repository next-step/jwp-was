package webserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.HttpMethod;
import webserver.http.RequestLine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestLineTest {

    @Test
    void GET_요청_파싱_테스트() {
        String requestMessage = "GET /users HTTP/1.1";

        RequestLine requestLine = new RequestLine(requestMessage);

        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
        );
    }

    @Test
    void POST_요청_파싱_테스트() {
        String requestMessage = "POST /users HTTP/1.1";

        RequestLine requestLine = new RequestLine(requestMessage);

        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
        );
    }

    @Test
    void QUERY_STRING_파싱_테스트() {
        String requestMessage = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        String[] splitRequestMessage = requestMessage.split(" ");
        String[] splitPath = splitRequestMessage[1].split("\\?");
        RequestLine requestLine = new RequestLine(requestMessage);

        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getVersion()).isEqualTo("1.1"),
                () -> assertThat(requestLine.getQueryString()).isEqualTo(splitPath[1])
        );
    }

    @ParameterizedTest()
    @ValueSource(strings = {"GET /users", "GET /usersHTTP/1.1", "GET /users HTTP1.1", "GET HTTP/1.1"})
    void 잘못된유형의_REQUEST요청_테스트(String requestMessage) {
        RequestLine requestLine = new RequestLine(requestMessage);

        assertThat(requestLine.getPath()).isEqualTo("/error");
    }
}
