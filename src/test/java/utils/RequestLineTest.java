package utils;

import exception.NotExistHttpMethodException;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {

    private RequestLine requestLine = RequestLine.getInstance();

    @Test
    @DisplayName("요청 정보에 따른 파싱 (get)")
    void parsing_get() {
        String data = "GET /users HTTP/1.1";
        RequestLine parsing = requestLine.parsing(data);
        assertThat(parsing.getMethod()).isEqualTo(HttpMethod.GET);
        assertThatRequestData(parsing);
    }

    @Test
    @DisplayName("요청정보에 해당하지 않는 http Method가 들어올 경우 예외처리")
    void exception_invalid_http_method() {
        String data = "GETT /users HTTP/1.1";
        assertThatThrownBy(() -> requestLine.parsing(data))
                .isInstanceOf(NotExistHttpMethodException.class);
    }

    @Test
    @DisplayName("요청 정보에 따른 파싱 (post)")
    void parsing_post() {
        String data = "POST /users HTTP/1.1";
        RequestLine parsing = requestLine.parsing(data);
        assertThat(parsing.getMethod()).isEqualTo(HttpMethod.POST);
        assertThatRequestData(parsing);
    }

    private void assertThatRequestData(RequestLine parsing) {
        assertThat(parsing.getPath()).isEqualTo("/users");
        assertThat(parsing.getProtocol()).isEqualTo("HTTP");
        assertThat(parsing.getVersion()).isEqualTo("1.1");
    }

    @Test
    @DisplayName("queryParam 파싱 테스트")
    void query_param() {
        String data = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        assertThat(requestLine.getQueryParam(data)).isEqualTo("userId=javajigi&password=password&name=JaeSung");
    }

    @Test
    @DisplayName("queryParam")
    void query_param_get() {
        String requestUri = "/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        User saveUser = requestLine.queryStringToUser(requestUri);
        User user = new User("javajigi", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net");
        assertThat(saveUser).isEqualTo(user);
    }
}