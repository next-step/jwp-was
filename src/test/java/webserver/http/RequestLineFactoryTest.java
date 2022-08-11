package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.exception.NotFoundHttpMethodException;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("RequestLineFactory 테스트")
class RequestLineFactoryTest {

    @DisplayName("HttpMethod 를 찾을 수 없는 경우")
    @Test
    void notFoundHttpMethod() {
        assertThatThrownBy(() -> parsing("DELETE /users?userId=jdragon HTTP/1.1"))
                .isInstanceOf(NotFoundHttpMethodException.class);
    }

    @DisplayName("GET 요청시 RequestLine 생성 테스트")
    @Test
    void getTest() {
        RequestLine requestLine = parsing("GET /docs/index.html HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/docs/index.html");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("POST 요청시 RequestLine 생성 테스트")
    @Test
    void postTest() {
        RequestLine requestLine = parsing("POST /docs/index.html HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/docs/index.html");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("GET 쿼리 스트링 테스트")
    @Test
    void queryString() {
        RequestLine requestLine = parsing("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getParameters()).contains(
                Map.entry("userId", "javajigi"),
                Map.entry("password", "password"),
                Map.entry("name", "JaeSung"));
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    private RequestLine parsing(String httpRequest) {
        return RequestLineFactory.parsing(httpRequest);
    }

}
