package webserver;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void requestMethodTestInRequestLine() {
        assertThat(RequestParser.parseMethod("GET /users HTTP/1.1")).isEqualTo("GET");
    }

    @Test
    void requestPathTestInRequestLine() {
        assertThat(RequestParser.parsePath("GET /users HTTP/1.1")).isEqualTo("/users");
    }

    @Test
    void requestProtocolTestInRequestLine() {
        assertThat(RequestParser.parseProtocol("GET /users HTTP/1.1")).isEqualTo("HTTP");
    }

    @Test
    void requestProtocolVersionTestInRequestLine() {
        assertThat(RequestParser.parseProtocolVersion("GET /users HTTP/1.1")).isEqualTo("1.1");
    }

    @Test
    void requestQueryStringTestInRequestLine() {
        assertThat(RequestParser.parseQueryString("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1")).isEqualTo("userId=javajigi&password=password&name=JaeSung");
    }
}
