package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import db.DataBase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

    private static final String PORT = "8089";
    private static final String URL = "http://localhost:" + PORT;
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    private final RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    void databaseClear() {
        DataBase.clear();
    }

    @BeforeAll
    static void setUp() {
        EXECUTOR.execute(() -> {
            try {
                WebApplicationServer.main(new String[]{PORT});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @AfterAll
    static void tearDown() {
        EXECUTOR.shutdown();
    }

    @DisplayName("초기 화면")
    @Test
    void request_resttemplate() {
        ResponseEntity<String> response = restTemplate.getForEntity(URL + "/index.html", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("회원가입 후 첫 화면으로 이동한다")
    @Test
    void create_user() {
        final ResponseEntity<String> 회원가입_요청 = 회원가입_요청("admin", "password");

        assertThat(회원가입_요청.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(회원가입_요청.getHeaders().get("Location")).containsExactly("/index.html");

    }

    @DisplayName("로그인 실패")
    @Test
    void login_failed() {
        회원가입_요청("admin", "pass");

        ResponseEntity<String> response = 로그인_요청("admin", "password");

        assertAll(
            () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND),
            () -> assertThat(response.getHeaders().get("Location")).containsExactly("/user/login_failed.html")
        );
    }

    @DisplayName("로그인 성공")
    @Test
    void login_success() {
        회원가입_요청("administrator", "password");

        ResponseEntity<String> response = 로그인_요청("administrator", "password");

        assertAll(
            () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND),
            () -> assertThat(response.getHeaders().get("Location")).containsExactly("/index.html")
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
        final String sessionId = getSessionId(회원가입_요청("administrator", "password"));

        final ResponseEntity<String> response = 로그인_상태로_사용자_목록_조회("administrator", "password", sessionId);

        assertAll(
            () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(response.getBody()).contains("<td>administrator</td>")
        );
    }

    private static String getSessionId(final ResponseEntity<String> 회원가입_요청) {
        return 회원가입_요청.getHeaders().get("Set-Cookie").stream()
            .filter(cookie -> cookie.startsWith("JWP_SESSION_ID"))
            .findFirst()
            .map(cookie -> cookie.split(";")[0])
            .map(cookie -> cookie.split("=")[1])
            .orElseThrow(() -> new RuntimeException("Cookie not found"));
    }

    private ResponseEntity<String> 로그인_상태로_사용자_목록_조회(String userId, String password, String sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "JWP_SESSION_ID=" + sessionId);

        final String loginParams = String.format("userId=%s&password=%s", userId, password);

        restTemplate.exchange(URL + "/user/login", HttpMethod.POST, new HttpEntity<>(loginParams, headers),
            String.class);

        return restTemplate.exchange(URL + "/user/list.html", HttpMethod.GET, new HttpEntity<>(headers),
            String.class);
    }

    private ResponseEntity<String> 사용자_목록_조회() {
        return restTemplate.getForEntity(URL + "/user/list.html", String.class);
    }

    private ResponseEntity<String> 로그인_요청(final String userId, final String password) {
        final String loginParams = String.format("userId=%s&password=%s", userId, password);

        return restTemplate.postForEntity(URL + "/user/login", loginParams, String.class);
    }

    private ResponseEntity<String> 회원가입_요청(final String userId, final String password) {
        final String createUserParams = String.format("userId=%s&password=%s&name=administrator&email=admin@email.com", userId, password);

        return restTemplate.postForEntity(URL + "/user/create", createUserParams, String.class);
    }
}
