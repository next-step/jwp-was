package webserver;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void 회원가입페이지_요청() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/user/form.html", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void 회원가입_요청() {
        RestTemplate restTemplate = new RestTemplate();
        String requestBody = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/user/create", requestBody, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().get("Location")).contains("/index.html");
    }

    @Test
    void 로그인_성공_요청() {
        RestTemplate restTemplate = new RestTemplate();
        String requestBody = "userId=user1&password=1234";
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/user/login", requestBody, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().get("Set-Cookie")).contains("logined=true; Path=/");
        assertThat(response.getHeaders().get("Location")).contains("/index.html");
    }

    @Test
    void 로그인_실패_요청() {
        RestTemplate restTemplate = new RestTemplate();
        String requestBody = "userId=user4&password=1234";
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/user/login", requestBody, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().get("Set-Cookie")).contains("logined=false; Path=/");
        assertThat(response.getHeaders().get("Location")).contains("/user/login_failed.html");
    }

    @Test
    void 로그인후_사용자목록_요청() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "logined=true");
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/user/list",
                HttpMethod.GET,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void 로그인안한상태로_사용자목록_요청() {
        RestTemplate restTemplate = new RestTemplate(
                new SimpleClientHttpRequestFactory() {
                    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException, IOException {
                        super.prepareConnection(connection, httpMethod);
                        connection.setInstanceFollowRedirects(false);
                    }
                }
        );

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/user/list", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().get("Location")).contains("/user/login.html");
    }
}
