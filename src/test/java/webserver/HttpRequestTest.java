package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import utils.FileIoUtils;

class HttpRequestTest {

    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("GET /index.html 요청 시, index.html 파일을 읽어 클라이언트에 응답한다.")
    @Test
    void request_index() throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new String(body));
    }

    @DisplayName("GET /user/form.html 요청 시, 회원가입 페이지 HTML 파일을 읽어 클라이언트에 응답한다.")
    @Test
    void request_form() throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/user/form.html", String.class);
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new String(body));
    }
}
