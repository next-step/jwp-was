package webserver;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import db.DataBase;
import utils.FileIoUtils;

class HttpRequestTest {
    private static final String LOCALHOST = "http://localhost:8080";
    private static final String INDEX_HTML_FILE_PATH = "./templates/index.html";
    private static final ExecutorService webApplicationServer = Executors.newSingleThreadExecutor();

    @BeforeAll
    static void setup() {
        webApplicationServer.execute(() -> {
            try {
                WebApplicationServer.main(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @AfterAll
    static void teardown() {
        webApplicationServer.shutdown();
    }

    @DisplayName("GET /index.html 요청 시, index.html 파일이 응답 되어야 한다.")
    @Test
    void getRequestIndexHtml() throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(LOCALHOST + "/index.html", String.class);
        byte[] expect = FileIoUtils.loadFileFromClasspath(INDEX_HTML_FILE_PATH);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new String(expect));
    }

    @DisplayName("Get /user/index.html 요청 시, 200 응답이 되어야 한다.")
    @Test
    void getUserIndexHtml() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(LOCALHOST + "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void requestRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(LOCALHOST, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
