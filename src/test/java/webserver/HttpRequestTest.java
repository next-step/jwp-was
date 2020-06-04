package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    private static final String HOST = "http://localhost";
    private static final String PORT = "8080";
    private static final String TEST_URL = HOST + (PORT != null || PORT.isEmpty() ? ":" + PORT : "");

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    @Test
    void request_resttemplate() {
        String resourceUrl = "https://edu.nextstep.camp";
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity(resourceUrl + "/c/4YUvqn9V", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("테스트를 위해서 서버를 띄우고 시작")
    void localhostTest() {
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity(TEST_URL, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
