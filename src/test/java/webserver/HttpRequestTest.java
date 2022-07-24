package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class HttpRequestTest {

    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    void request_resttemplate() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("로그인 실패")
    @Test
    void login_failed() {
        회원가입_요청("admin", "pass");

        ResponseEntity<String> response = 로그인_요청("admin", "password");

        assertAll(
            () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND),
            () -> assertThat(response.getHeaders().get("Location")).containsExactly("/user/login_failed.html"),
            () -> assertThat(response.getHeaders().get("Set-Cookie")).containsExactly("logined=false; Path=/")
        );
    }

    private ResponseEntity<String> 로그인_요청(final String userId, final String password) {
        final String loginParams = String.format("userId=%s&password=%s", userId, password);

        return restTemplate.postForEntity("http://localhost:8080/user/login", loginParams, String.class);
    }

    private void 회원가입_요청(final String userId, final String password) {
        final String createUserParams = String.format("userId=%s&password=%s&name=관리자&email=admin@email.com", userId, password);

        restTemplate.postForEntity("http://localhost:8080/user/create", createUserParams, String.class);
    }
}
