package http.httprequest.requestline;

import http.httprequest.requestline.Path;
import http.httprequest.requestline.Protocol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import http.httprequest.requestline.HttpMethod;
import http.httprequest.requestline.RequestLine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class RequestLineTest {

    @Test
    @DisplayName("Get 요청을 정상적으로 requestLine객체로 생성하는지 테스트")
    void getRequestParsingTest() {
        String requestLine = "GET /users HTTP/1.1";
        Path path = Path.from("/users");
        Protocol protocol = Protocol.from("HTTP/1.1");
        RequestLine expected = new RequestLine(HttpMethod.GET, path, protocol);

        RequestLine result = RequestLine.from(requestLine);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Post 요청을 정상적으로 requestLine객체로 생성하는지 테스트")
    void postRequestParsingTest() {
        String requestLine = "POST /users HTTP/1.1";
        Path path = Path.from("/users");
        Protocol protocol = Protocol.from("HTTP/1.1");
        RequestLine expected = new RequestLine(HttpMethod.POST, path, protocol);

        RequestLine result = RequestLine.from(requestLine);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("HTTP REQUEST 양식에 맞지 않는 값을 요청했을때 IllegalArgumentException이 나오는지 테스트")
    void exceptionTest() {
        String requestLine = "GET /usersHTTP/1.1";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> RequestLine.from(requestLine))
                .withMessage("HTTP REQUEST 형식에 맞지 않습니다. request: " + requestLine);
    }
}