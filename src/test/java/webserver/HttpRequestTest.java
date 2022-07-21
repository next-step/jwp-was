package webserver;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    private static final String VALID_USER_ID = "user";
    private static final String VALID_PASSWORD = "pass";
    private static final String INVALID_USER_ID = "user11";
    private static final String INVALID_PASSWORD = "pass11";

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
        
        DataBase.addUser(new User(VALID_USER_ID, VALID_PASSWORD, "유저", "user@email.com"));
    }

    @AfterAll
    static void teardown() {
        EXECUTOR.shutdown();
    }

    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("GET /index.html 요청 시, index.html 파일을 읽어 클라이언트에 응답한다.")
    @Test
    void request_index() throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new String(body));
    }

    @DisplayName("GET /user/form.html 요청 시, 회원가입 페이지 HTML 파일을 읽어 클라이언트에 응답한다.")
    @Test
    void request_form() throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/user/form.html", String.class);
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new String(body));
    }

    @DisplayName("GET /user/login.html 요청 시, 로그인 페이지 HTML 파일을 읽어 클라이언트에 응답한다.")
    @Test
    void request_login() throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/user/login.html", String.class);
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new String(body));
    }

    @DisplayName("로그인 성공 시, 로그인 성공 쿠키와 함께 index.html 페이지로 리다이렉트한다.")
    @Test
    void request_login_success() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("userId", VALID_USER_ID);
        requestBody.add("password", VALID_PASSWORD);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/user/login", requestBody, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().getLocation()).isEqualTo(URI.create("/index.html"));
        assertThat(response.getHeaders().get("Set-Cookie")).contains("logined=true; Path=/");
    }
}
