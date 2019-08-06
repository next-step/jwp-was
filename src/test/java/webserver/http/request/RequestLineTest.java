package webserver.http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;

import static org.assertj.core.api.Java6Assertions.assertThat;

class RequestLineTest {

    private RequestLine requestLine;

    @BeforeEach
    void setUp() {
        requestLine = RequestLine.parse("GET /create?userId=jaeyeonling HTTP/1.1");
    }

    @DisplayName("RequestLine을 생성한다.")
    @Test
    void parse() {
        // then
        assertThat(requestLine).isNotNull();
    }


    @DisplayName("Method를 가져온다.")
    @Test
    void getMethod() {
        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
    }


    @DisplayName("Path를 가져온다.")
    @Test
    void getPath() {
        // then
        assertThat(requestLine.getPath()).isEqualTo("/create");
    }

    @DisplayName("파라미터를 가져온다.")
    @Test
    void getParameter() {
        // when
        final String userId = requestLine.getParameter("userId");

        // then
        assertThat(userId).isEqualTo("jaeyeonling");
    }
}
