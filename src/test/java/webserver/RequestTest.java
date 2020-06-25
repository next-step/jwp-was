package webserver;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {

    @Test
    void request_RestTemplate() {

        // given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/index.html";

        // when
        List<HttpStatus> responseStatuses = IntStream.range(0, 400)
                .parallel()
                .mapToObj(i -> restTemplate.getForEntity(url, String.class).getStatusCode())
                .collect(Collectors.toList());

        // then
        assertThat(responseStatuses)
                .containsOnly(HttpStatus.OK);
    }
}
