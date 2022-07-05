package webserver;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

class WebApplicationServerTest {

    @DisplayName("400 개의 요청을 보내, 서버가 정상적으로 응답해야한다.")
    @Test
    void send400Requests() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/index.html";

        List<HttpStatus> httpStatuses = IntStream.range(0, 400)
                .parallel()
                .mapToObj(__ -> restTemplate.getForEntity(url, String.class).getStatusCode())
                .collect(Collectors.toList());

        assertThat(httpStatuses).containsOnly(HttpStatus.OK);
    }
}
