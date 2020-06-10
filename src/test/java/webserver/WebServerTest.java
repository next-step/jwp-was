package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

@DisplayName("웹 서버 테스트")
class WebServerTest {
    @Test
    void threadPool() throws Exception {
        // thread pool core size 5, max 10, queue 10 정도로 하고 아래 코드를 실행하면 reject 를 볼 수 있음
        Thread thread = new Thread(() -> {
            try {
                WebServer.main(new String[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(2000);

        RestTemplate restTemplate = new RestTemplate();
        AtomicInteger idx = new AtomicInteger(0);
        for (int i = 0 ; i < 1000 ; ++i) {
            System.out.println(i + " 번째 요청을 추가합니다.");

            Thread req = new Thread(() -> {
                try {
                    ResponseEntity<String> response = restTemplate.getForEntity(
                            "http://localhost:8080/index.html?idx=" + idx.getAndAdd(1),
                            String.class);

                    System.out.println(response.getStatusCode());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            req.start();
        }

        Thread.sleep(2000);
    }

}
