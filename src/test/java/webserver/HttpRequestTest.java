package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

    @DisplayName("로그인 성공")
    @Test
    void login_success() {
        회원가입_요청("administrator", "password");

        ResponseEntity<String> response = 로그인_요청("administrator", "password");

        assertAll(
            () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND),
            () -> assertThat(response.getHeaders().get("Location")).containsExactly("/index.html"),
            () -> assertThat(response.getHeaders().get("Set-Cookie")).containsExactly("logined=true; Path=/")
        );
    }

    @DisplayName("로그인 하지 않은 상태에서 사용자 목록 페이지 조회시 로그인화면으로 이동한다")
    @Test
    void not_logged_in_cannot_access_user_list() {
        final ResponseEntity<String> response = 사용자_목록_조회();

        assertAll(
            () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(response.getBody()).contains("<form name=\"question\" method=\"post\" action=\"/user/login\">")
                .doesNotContain("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요")
        );
    }

    @DisplayName("로그인 상태에서 사용자 목록 페이지 조회시 사용자 목록을 조회한다.")
    @Test
    void logged_in_access_user_list() {
        회원가입_요청("administrator", "password");

        final ResponseEntity<String> response = 로그인_상태로_사용자_목록_조회();

        assertAll(
            () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(response.getBody()).contains("<td>administrator</td>")
        );
    }

    private ResponseEntity<String> 로그인_상태로_사용자_목록_조회() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "logined=true");

        return restTemplate.exchange("http://localhost:8080/user/list.html", HttpMethod.GET, new HttpEntity<>(headers),
            String.class);
    }

    private ResponseEntity<String> 사용자_목록_조회() {
        return restTemplate.getForEntity("http://localhost:8080/user/list.html", String.class);
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
