package webserver;

import db.DataBase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class RestTemplateTest {

    private static final Thread WAS_SERVER = new Thread(() -> {
        String[] args = {"8080", "5", "10"};
        try {
            WebApplicationServer.main(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });

    @BeforeAll
    static void beforeAll() {
        WAS_SERVER.start();
    }

    @BeforeEach
    void setUp() {
        DataBase.deleteAll();
    }

    @AfterAll
    static void afterAll() {
        WAS_SERVER.interrupt();
    }

    @Test
    void test() {
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 20; i++) {
            ResponseEntity<String> response =
                    restTemplate.postForEntity("http://localhost:8080/user/create", "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net", String.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        }
    }

    @Test
    void request_resttemplate_index() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void create() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response =
                restTemplate.postForEntity("http://localhost:8080/user/create", "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

}
