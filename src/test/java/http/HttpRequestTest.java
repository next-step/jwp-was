package http;

import http.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    @Test
    @DisplayName("GET을 파싱하는 테스트")
    void parseGetRequestTest() throws Exception {
        final InputStream in = new FileInputStream(new File("./src/test/resources/HTTP_GET.txt"));
        final HttpRequest request = HttpRequest.from(in);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("jinwoo");
    }

    @Test
    @DisplayName("POST 파싱하는 테스트")
    void parsePostRequestTest() throws Exception{
        final InputStream in = new FileInputStream(new File("./src/test/resources/HTTP_POST.txt"));
        final HttpRequest request = HttpRequest.from(in);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("name")).isEqualTo("JWLEE");
    }
}
