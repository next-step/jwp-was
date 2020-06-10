package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("웹서버 테스트")
class WebServerTest {

    private static final Logger log = LoggerFactory.getLogger(WebServerTest.class);

    private Thread thread;

    @DisplayName("웹서버 스레드 세팅")
    @BeforeEach
    void setUp() {
        thread = new Thread(() -> {
            // 스프링 부트처럼 SpringBootApplication.run() 방식으로 변경해도 좋을 것 같은데..
            try {
                WebServer.main(null);
            } catch (Exception e) {
                // 망했지 뭐..
            }
        });
        thread.setName("I am potato");
    }

    @DisplayName("1k 요청 테스트!")
    @Test
    void connection_test() throws Exception {
        thread.start();

        final int requestCount = 10;
        final RestTemplate restTemplate = new RestTemplate();
        final String targetUrl = "http://localhost:8080/index.html";
        final ExecutorService requestPool = Executors.newFixedThreadPool(requestCount);

        for (int i = 0; i < requestCount; i++) {
            requestPool.execute(() -> {
                final ResponseEntity<String> response = restTemplate.getForEntity(targetUrl, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            });
        }

        // 웹 서버 스레드가 테스트 동안 떠 있도록 하고 만약의 참사에 대비해 타임아웃을 둔다 'ㅁ'
        thread.join(5000);
    }
}