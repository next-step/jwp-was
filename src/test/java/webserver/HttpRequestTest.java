package webserver;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    @Test
    void request_resttemplate() {
        String resourceUrl = "https://edu.nextstep.camp";
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity(resourceUrl + "/c/4YUvqn9V", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
