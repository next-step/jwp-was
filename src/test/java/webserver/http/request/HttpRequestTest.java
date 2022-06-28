package webserver.http.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Header;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

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
    void getRequestParameters() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "HttpGet.txt"));
        HttpRequest httpRequest = HttpRequest.of(new BufferedReader(new InputStreamReader(in)));

        assertThat(httpRequest.getRequestParameters().get("userId")).isEqualTo("dean");
        assertThat(httpRequest.getMethod()).isEqualTo(Method.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
    }

    @DisplayName("RequestBody 값을 알수 있다")
    @Test
    void getRequestBody() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "HttpPost.txt"));
        HttpRequest httpRequest = HttpRequest.of(new BufferedReader(new InputStreamReader(in)));

        assertThat(httpRequest.getRequestBody().get("userId")).isEqualTo("dean");
        assertThat(httpRequest.getMethod()).isEqualTo(Method.POST);
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
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
