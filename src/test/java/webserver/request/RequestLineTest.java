package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static webserver.request.RequestLine.INDEX_OF_METHOD;
import static webserver.request.RequestLine.INDEX_OF_URL;

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
        HttpMethod method = HttpMethod.valueOf(expectedResult[INDEX_OF_METHOD]);

        // then
        assertThat(requestLine.getMethod()).isEqualTo(method);
        assertThat(requestLine.getUri().getPath()).isEqualTo(expectedResult[INDEX_OF_URL]);
    }

    @DisplayName("요청 시 path를 구하는데 성공한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "GET /users HTTP/1.1",
            "POST /users HTTP/1.1",
            "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1",
            "GET /users?userId=javajigi HTTP/1.1"
    })
    void requestLine_thenSuccess(String request) {
        // when
        RequestLine requestLine = RequestLine.parse(request);

        // then
        assertThat(requestLine.getUri().getPath()).isEqualTo("/users");
    }
}