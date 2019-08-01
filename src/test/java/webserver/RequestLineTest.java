package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RequestLineTest {

    @DisplayName("요청을 받았을 시 requestLine을 구하는데 성공한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "GET /users HTTP/1.1",
            "POST /users HTTP/1.1"})
    void getRequestLine_success(String request) {
        // when
        RequestLine requestLine = RequestLine.parse(request);
        String[] expectedResult = request.split(" ");
        HttpMethod method = HttpMethod.valueOf(expectedResult[0]);

        // then
        assertThat(requestLine.getMethod()).isEqualTo(method);
        assertThat(requestLine.getUrl().getPath()).isEqualTo(expectedResult[1]);
        assertThat(requestLine.getVersion()).isEqualTo(expectedResult[2]);
    }

    @DisplayName("GET 메소드 요청 시 requestLine을 생성한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "GET /users HTTP/1.1",
            "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1",
            "GET /users?userId=javajigi HTTP/1.1"
    })
    void requesetLine_GET_thenSuccess(String request) {
        // when
        RequestLine requestLine = RequestLine.parse(request);

        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getUrl().getPath()).isEqualTo("/users");
    }
}