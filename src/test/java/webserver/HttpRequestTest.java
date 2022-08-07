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
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void request_rest_template() {
        RestTemplate restTemplate = new RestTemplate();

        StopWatch sw = new StopWatch();
        sw.start();
        boolean actual = IntStream.range(0, 400)
                .parallel()
                .mapToObj(index -> restTemplate.getForEntity("http://localhost:8080", String.class))
                .allMatch(response -> response.getStatusCode().is2xxSuccessful());

        assertThat(actual).isTrue();
        sw.stop();
        System.out.println(("Total Elaspsed:" + sw.getTotalTimeSeconds()));
    }
}
