package webserver.request;

import http.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("HTTP 요청 테스트")
public class HttpRequestTest {

    private String testDirectory = "./src/test/resources/";

    @DisplayName("GET Http Request 생성")
    @Test
    void request_GET() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = HttpRequest.from(in);
        assertAll(
                () -> assertThat(request.getHttpMethod()).isEqualTo("GET"),
                () -> assertThat(request.getPath()).isEqualTo("/user/create"),
                () -> assertThat(request.getRequestHeader("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(request.getParameter("userId")).isEqualTo("javajigi")
        );
    }

    @DisplayName("Http Request의 header와 requestLine은 공백이어선 안된다.")
    @Test
    void httpRequestEmpty() throws FileNotFoundException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET_FAIL.txt"));
        assertThatThrownBy(
                () -> HttpRequest.from(in))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Content-Length 확인")
    @Test
    void checkContentLength() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = HttpRequest.from(in);
        assertThat(request.getContentLength()).isEqualTo(46);
    }

    @DisplayName("POST Http Request 생성")
    @Test
    void request_POST() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = HttpRequest.from(in);
        assertAll(
                () -> assertThat(request.getHttpMethod()).isEqualTo("POST"),
                () -> assertThat(request.getPath()).isEqualTo("/user/create"),
                () -> assertThat(request.getRequestHeader("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(request.getBody("userId")).isEqualTo("javajigi")
        );
    }
}
