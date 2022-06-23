package webserver.http.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Header;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @DisplayName("Http Request 객체를 생성할수 있다.")
    @Test
    void create() {
        Assertions.assertThatCode(() -> new HttpRequest(
                RequestLine.parse("GET /users HTTP/1.1"),
                          new Header(Collections.emptyMap(), Collections.emptyMap()), null))
                  .doesNotThrowAnyException();
    }

    @DisplayName("요청 경로를 알수 있다")
    @Test
    void getPath() {
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /users HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), null);
        assertThat(httpRequest.getPath()).isEqualTo("/users");
    }

    @DisplayName("Http Method를 알수 있다")
    @Test
    void getMethod() {
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /users HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), null);
        assertThat(httpRequest.getMethod()).isEqualTo(Method.GET);
    }

    @DisplayName("RequestParameters를 알수 있다")
    @Test
    void getRequestParameters() {
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /users?username=dean HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), null);
        assertThat(httpRequest.getRequestParameters().get("username")).isEqualTo("dean");
    }

    @DisplayName("Cookie 값을 알수 있다")
    @Test
    void getRequestBody() {
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /users HTTP/1.1"),
                new Header(Collections.emptyMap(), Map.of("logined", "true")), null);
        assertThat(httpRequest.getCookie("logined")).isEqualTo("true");
    }

    @DisplayName("로그인 여부를 알수 있다.")
    @Test
    void isLogined() {
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /users HTTP/1.1"),
                new Header(Collections.emptyMap(), Map.of("logined", "true")), null);
        assertThat(httpRequest.isLogined()).isTrue();
    }

}
