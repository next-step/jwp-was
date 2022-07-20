package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import webserver.enums.HttpMethod;
import webserver.enums.RequestProtocol;

class HttpRequestTest {

    @Test
    void createTest() {
        HttpRequest httpRequest = new HttpRequest("GET /users HTTP/1.1");

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/users");
        assertThat(httpRequest.getProtocol()).isEqualTo(RequestProtocol.HTTP_1_1);
    }

    @Disabled("로컬 8080 요청/응답 테스트 (서버 동작 후 테스팅 가능)")
    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
