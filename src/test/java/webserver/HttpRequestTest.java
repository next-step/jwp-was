package webserver;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    @Test
    void requestTest() {

        StopWatch sw = new StopWatch();
        IntStream.range(0, 50)
                .parallel()
                .forEach(index -> {
                    System.out.println("current index : " + index);
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/request-test", String.class);
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                });
        sw.stop();
        System.out.println(("Total Elaspsed:" + sw.getTotalTimeSeconds()));

    }

}
