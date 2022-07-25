package webserver;

import exception.InvalidRequestException;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestLineTest {

    @DisplayName("요구사항 1 - GET 요청")
    @Test
    void requestLineFromGetRequest() {
        assertThat(new RequestLine("GET /users HTTP/1.1")).isEqualTo(new RequestLine("GET", "/users", "HTTP/1.1"));
    }

    @DisplayName("요구사항 2 - POST 요청")
    @Test
    void requestLineFromPostRequest() {
        assertThat(new RequestLine("POST /users HTTP/1.1")).isEqualTo(new RequestLine("POST", "/users", "HTTP/1.1"));
    }

    @DisplayName("요구사항 3 - Query String 파싱")
    @Test
    void requestLineFromGetRequest_WithQueryString() {
        assertThat(new RequestLine("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1"))
                .isEqualTo(new RequestLine("GET", "/users?userId=javajigi&password=password&name=JaeSung", "HTTP/1.1"));
    }

    @DisplayName("잘못된 경로로 요청")
    @Test
    void requestLineFromGetRequest_WithWrongPath() {
        assertThatThrownBy(() -> new RequestLine("GET  HTTP/1.1"))
                .isInstanceOf(InvalidRequestException.class);
    }

    @DisplayName("기능 요구사항 2 - RequestLine compare user")
    @Test
    void requestLineFromPostRequest_WithCreateUser() {
        RequestLine requestLine = new RequestLine("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");
        Map<String, String> requestQueryString = requestLine.getQueryStringWithoutPathFromPath();

        assertThat(new User(requestQueryString.get("userId"), requestQueryString.get("password"), requestQueryString.get("name"), requestQueryString.get("email"))).isEqualTo(new User("javajigi", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net"));
    }

}