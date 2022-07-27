package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import db.DataBase;
import model.User;
import utils.FileIoUtils;

class WebApplicationServerTest {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String VALID_USER_ID = "user";
    private static final String VALID_PASSWORD = "pass";
    private static final String INVALID_USER_ID = "user11";
    private static final String INVALID_PASSWORD = "pass11";
    private static final String USER_NAME = "사용자";
    private static final String USER_EMAIL = "user@example.com";
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    @BeforeAll
    static void setup() {
        EXECUTOR.execute(() -> {
            try {
                WebApplicationServer.main(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        DataBase.addUser(new User(VALID_USER_ID, VALID_PASSWORD, USER_NAME, USER_EMAIL));
    }

    @AfterAll
    static void teardown() {
        EXECUTOR.shutdown();
    }

    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("GET /index.html 요청 시, index.html 파일을 읽어 클라이언트에 응답한다.")
    @Test
    void request_index() throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/index.html", String.class);
        byte[] expectedBody = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new String(expectedBody));
    }

    @DisplayName("GET /user/form.html 요청 시, 회원가입 페이지 HTML 파일을 읽어 클라이언트에 응답한다.")
    @Test
    void request_form() throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/user/form.html", String.class);
        byte[] expectedBody = FileIoUtils.loadFileFromClasspath("./templates/user/form.html");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new String(expectedBody));
    }

    @DisplayName("GET /user/login.html 요청 시, 로그인 페이지 HTML 파일을 읽어 클라이언트에 응답한다.")
    @Test
    void request_login() throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/user/login.html", String.class);
        byte[] expectedBody = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new String(expectedBody));
    }

    @DisplayName("로그인 성공 시, 로그인 성공 쿠키와 함께 index.html 페이지로 리다이렉트한다.")
    @Test
    void request_login_success() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("userId", VALID_USER_ID);
        requestBody.add("password", VALID_PASSWORD);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/user/login", requestBody, String.class);
        HttpHeaders headers = response.getHeaders();
        String cookie = headers.get("Set-Cookie").get(0);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(headers.getLocation()).isEqualTo(URI.create("/index.html"));
        assertThat(cookie).contains("logined=true", "Path=/");
    }

    @DisplayName("로그인 실패 시, 로그인 실패 쿠키와 함께 login_failed.html 페이지로 리다이렉트한다.")
    @Test
    void request_login_failure() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("userId", INVALID_USER_ID);
        requestBody.add("password", INVALID_PASSWORD);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/user/login", requestBody, String.class);
        HttpHeaders headers = response.getHeaders();
        String cookie = headers.get("Set-Cookie").get(0);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(headers.getLocation()).isEqualTo(URI.create("/user/login_failed.html"));
        assertThat(cookie).contains("logined=false");
    }

    @DisplayName("로그인 상태인 경우, GET /user/list 요청 시 사용자 목록을 출력한다.")
    @Test
    void request_user_list_with_logged_in() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "logined=true; Path=/");
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/user/list", HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(VALID_USER_ID, USER_NAME, USER_EMAIL);
    }

    @DisplayName("비로그인 상태인 경우, GET /user/list 요청 시 로그인 페이지로 이동한다.")
    @Test
    void request_user_list_without_logged_in() throws IOException, URISyntaxException {
        HttpEntity<Object> entity = new HttpEntity<>(null);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/user/list", HttpMethod.GET, entity, String.class);
        String expectedBody = new String(FileIoUtils.loadFileFromClasspath("./templates/user/login.html"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedBody);
    }

    @DisplayName("클라이언트 요청 쿠키에 세션 ID가 존재하지 않으면, 세션 ID를 생성하여 쿠키에 저장 후 응답을 내려준다.")
    @Test
    void request_without_session_id() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL, String.class);
        String cookie = String.join("", response.getHeaders().get("Set-Cookie"));
        assertThat(cookie).contains("JSESSIONID");
    }

    @DisplayName("서로 다른 클라이언트가 요청을 보낼 경우, 각각의 클라이언트에 대한 세션이 생성된다.")
    @Test
    void request_by_different_clients() {
        RestTemplate restTemplate = new RestTemplate();

        // 클라이언트1 요청
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL, String.class);
        String clientSessionId = String.join("", response.getHeaders().get("Set-Cookie")).split("=")[1];

        // 클라이언트2 요청
        response = restTemplate.getForEntity(BASE_URL, String.class);
        String otherSessionId = String.join("", response.getHeaders().get("Set-Cookie")).split("=")[1];

        assertThat(clientSessionId).isNotEqualTo(otherSessionId);
    }

    @DisplayName("동일한 클라이언트가 요청을 보낼 경우, 응답 헤더에 세션 ID를 담지 않는다.")
    @Test
    void request_by_same_client() {
        // 클라이언트 최초 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL, String.class);
        String cookie = String.join("", response.getHeaders().get("Set-Cookie"));
        String sessionId = cookie.split("=")[1];
        assertThat(cookie).contains("JSESSIONID");

        // 클라이언트 재요청
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        response = restTemplate.exchange(BASE_URL, HttpMethod.GET, entity, String.class);
        assertThat(response.getHeaders().get("Set-Cookie")).isNull();
    }
}
