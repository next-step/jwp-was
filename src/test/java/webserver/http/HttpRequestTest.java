package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("HttpRequest 테스트")
class HttpRequestTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    @DisplayName("GET HttpRequest 생성")
    @Test
    void get() throws IOException {
        InputStream fileInputStream = new FileInputStream(new File(TEST_DIRECTORY + "http_get.txt"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        HttpRequest request = HttpRequest.of(bufferedReader);

        assertAll(
                () -> assertThat(request.getHttpMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(request.getPath()).isEqualTo("/docs/index.html"),
                () -> assertThat(request.getHeader()).contains(
                        Map.entry(HttpHeaders.HOST, "www.nowhere123.com"),
                        Map.entry(HttpHeaders.ACCEPT, "image/gif, image/jpeg, */*"))
        );

    }

    @DisplayName("POST HttpRequest 생성")
    @Test
    void post() throws IOException {
        InputStream fileInputStream = new FileInputStream(new File(TEST_DIRECTORY + "http_post.txt"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        HttpRequest request = HttpRequest.of(bufferedReader);

        assertAll(
                () -> assertThat(request.getHttpMethod()).isEqualTo(HttpMethod.POST),
                () -> assertThat(request.getPath()).isEqualTo("/user/create"),
                () -> assertThat(request.getHeader()).contains(
                        Map.entry(HttpHeaders.HOST, "localhost:8080"),
                        Map.entry(HttpHeaders.CONNECTION, "keep-alive"),
                        Map.entry(HttpHeaders.CONTENT_LENGTH, "46"),
                        Map.entry(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded"),
                        Map.entry(HttpHeaders.ACCEPT, "*/*")),
                () -> assertThat(request.getParameter("userId")).isEqualTo("javajigi"),
                () -> assertThat(request.getParameter("password")).isEqualTo("password"),
                () -> assertThat(request.getParameter("name")).isEqualTo("JaeSung")
        );
    }

}
