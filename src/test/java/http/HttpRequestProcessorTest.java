package http;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpRequestProcessorTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("/index.html GET 요청 처리 테스트")
    void createGETRequestTest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = HttpRequestProcessor.createRequest(in);
        assertThat(request.getPath()).isEqualTo("/index.html");
        assertThat(request.getHeader(HttpHeaderNames.HOST.toString())).isEqualTo("localhost:8080");
        assertThat(request.getHeader(HttpHeaderNames.CONNECTION.toString())).isEqualTo("keep-alive");
        assertThat(request.getHeader(HttpHeaderNames.ACCEPT.toString())).isEqualTo("*/*");
    }

    @Test
    @DisplayName("/user/create POST 요청 처리 테스트")
    void createPOSTRequestTest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_Post.txt"));
        HttpRequest request = HttpRequestProcessor.createRequest(in);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader(HttpHeaderNames.HOST.toString())).isEqualTo("localhost:8080");
        assertThat(request.getHeader(HttpHeaderNames.CONNECTION.toString())).isEqualTo("keep-alive");
        assertThat(request.getHeader(HttpHeaderNames.CONTENT_LENGTH.toString())).isEqualTo("46");
        assertThat(request.getHeader(HttpHeaderNames.CONTENT_TYPE.toString())).isEqualTo("application/x-www-form-urlencoded");
        assertThat(request.getHeader(HttpHeaderNames.ACCEPT.toString())).isEqualTo("*/*");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
        assertThat(request.getParameter("password")).isEqualTo("password");
        assertThat(request.getParameter("name")).isEqualTo("JaeSung");
    }
}
