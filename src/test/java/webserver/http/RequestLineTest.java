package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RequestLineTest {

    @DisplayName("문자열을 받아 RequestLine을 생성한다")
    @ParameterizedTest(name = "{0}")
    @CsvSource({"GET /users HTTP/1.1", "POST /users HTTP/1.1"})
    void create(String requestLine) {
        assertThat(new RequestLine(requestLine)).isNotNull();
    }

    @DisplayName("잘못된 형식의 문자열의 경우 Exception이 발생한다")
    @ParameterizedTest(name = "잘못된 문자열 \"{0}\"")
    @CsvSource({"HELLO /users HTTP/1.1", "GET /users",})
    void invalidRequestLine(String requestLine) {
        assertThatThrownBy(() -> new RequestLine(requestLine))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Querystring이 포함된 문자열을 받아 RequestLine을 생성한다")
    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1",
            "POST /users?userId=test&password=1q2w3e4r&name=admin HTTP/1.1"
    })
    void querystringRequest(String requestLine) {
        assertThat(new RequestLine(requestLine)).isNotNull();
    }
}