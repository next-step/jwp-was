package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import utils.FileIoUtils;
import webserver.domain.HttpRequest;
import webserver.domain.Parameters;
import webserver.domain.Path;
import webserver.domain.Protocol;
import webserver.domain.RequestBody;
import webserver.domain.RequestLine;
import webserver.domain.Version;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    public static final String USERS_PATH = "/users";
    public static final String HTTP_PROTOCOL = "HTTP";
    public static final Version TEST_VERSION = Version.ONE_DOT_ONE;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void request_resttemplate() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("HTTP GET 요청에 대한 파싱 결과를 확인할 수 있다.")
    @Test
    void getRequestLine() {
        ResponseEntity<HttpRequest> response = restTemplate.getForEntity("http://localhost:8080/users", HttpRequest.class);
        HttpRequest httpRequest = response.getBody();
        RequestLine requestLine = Objects.requireNonNull(httpRequest).getRequestLine();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo(new Path(USERS_PATH, Parameters.emptyInstance()));
        assertThat(requestLine.getProtocol()).isEqualTo(new Protocol(HTTP_PROTOCOL, TEST_VERSION));
    }

    @DisplayName("HTTP POST 요청에 대한 파싱 결과를 확인할 수 있다.")
    @Test
    void postRequestLine() {
        ResponseEntity<HttpRequest> response = restTemplate.postForEntity("http://localhost:8080/users", new RequestBody("body"), HttpRequest.class);
        HttpRequest httpRequest = response.getBody();
        RequestLine requestLine = Objects.requireNonNull(httpRequest).getRequestLine();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo(new Path(USERS_PATH, Parameters.emptyInstance()));
        assertThat(requestLine.getProtocol()).isEqualTo(new Protocol(HTTP_PROTOCOL, TEST_VERSION));

    }

    @DisplayName("Query String 데이터를 파싱한 뒤 그 결과를 반환한다.")
    @Test
    void getQueryString() {
        ResponseEntity<HttpRequest> response = restTemplate.getForEntity("http://localhost:8080/users?userId=catsbi&password=password&name=hansol",
                HttpRequest.class);

        HttpRequest httpRequest = response.getBody();
        RequestLine requestLine = Objects.requireNonNull(httpRequest).getRequestLine();
        Parameters parameters = requestLine.getParameters();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath())
                .isEqualTo(new Path(USERS_PATH, Parameters.from("userId=catsbi&password=password&name=hansol")));
        assertThat(requestLine.getProtocol()).isEqualTo(new Protocol(HTTP_PROTOCOL, TEST_VERSION));
        assertThat(parameters.get("userId")).isEqualTo("catsbi");
        assertThat(parameters.get("password")).isEqualTo("password");
        assertThat(parameters.get("name")).isEqualTo("hansol");
    }

    @DisplayName("${host}/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.")
    @Test
    void requestIndexHtml() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        String expectedBody = new String(body);

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);

        assertThat(response.getBody()).isEqualTo(expectedBody);
    }
}
