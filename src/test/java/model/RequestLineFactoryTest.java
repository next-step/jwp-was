package model;

import exception.NotFoundHttpMethodException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("RequestLine 테스트")
class RequestLineFactoryTest {

    @DisplayName("HttpMethod 를 찾을 수 없는 경우")
    @Test
    void notFoundHttpMethod() {
        assertThatThrownBy(() -> RequestLineFactory.parsing("DELETE /users?userId=jdragon HTTP/1.1"))
                .isInstanceOf(NotFoundHttpMethodException.class);
    }

    @DisplayName("GET 요청시 RequestLine 생성 테스트")
    @Test
    void getTest() {
        RequestLine requestLine = RequestLineFactory.parsing("GET /docs/index.html HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/docs/index.html");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("POST 요청시 RequestLine 생성 테스트")
    @Test
    void postTest() {
        RequestLine requestLine = RequestLineFactory.parsing("POST /docs/index.html HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/docs/index.html");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("GET 쿼리 스트링 테스트")
    @Test
    void queryString() {
        RequestLine requestLine = RequestLineFactory.parsing("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getParameters()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

}
