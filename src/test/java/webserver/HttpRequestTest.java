package webserver;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.HttpHeader.ACCEPT;
import static webserver.http.HttpHeader.CONNECTION;

public class HttpRequestTest {
    private static final String TEST_RESOURCE_PATH = "./src/test/resources/%s";

    @Test
    @Disabled
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Get 요청을 파싱한다.")
    void request_GET() throws IOException {
        File file = new File(String.format(TEST_RESOURCE_PATH, "Http_GET.txt"));
        FileInputStream inputStream = new FileInputStream(file);

        HttpRequest actual = HttpRequestFactory.parse(inputStream);

        assertThat(actual.getRequestLine().getMethod().name()).isEqualTo("GET");
        assertThat(actual.getRequestLine().getPathValue()).isEqualTo("/user/create");
        assertThat(actual.getHttpHeaders().getHeader(CONNECTION)).isEqualTo("keep-alive");
        assertThat(actual.getHttpHeaders().getHeader(ACCEPT)).isEqualTo("*/*");
        assertThat(actual.getRequestLine().getParameter("userId")).isEqualTo("javajigi");
    }

    @Test
    @DisplayName("Post 요청을 파싱한다.")
    void request_POST() throws IOException {
        File file = new File(String.format(TEST_RESOURCE_PATH, "Http_POST.txt"));
        FileInputStream inputStream = new FileInputStream(file);

        HttpRequest actual = HttpRequestFactory.parse(inputStream);

        assertThat(actual.getRequestLine().getMethod().name()).isEqualTo("POST");
        assertThat(actual.getRequestLine().getPathValue()).isEqualTo("/user/create");
        assertThat(actual.getHttpHeaders().getHeader(CONNECTION)).isEqualTo("keep-alive");
        assertThat(actual.getHttpHeaders().getHeader(ACCEPT)).isEqualTo("*/*");
        assertThat(actual.getBody().getContents().get("userId")).isEqualTo("javajigi");
    }
}
