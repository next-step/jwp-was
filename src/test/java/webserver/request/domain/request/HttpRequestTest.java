package webserver.request.domain.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "HTTP_GET.txt");
        HttpRequest request = new HttpRequest(in);

        Assertions.assertThat(request.getMethod().isGet()).isTrue();
        Assertions.assertThat(request.getPath()).isEqualTo("/user/create");
        Assertions.assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        Assertions.assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "HTTP_POST.txt");
        HttpRequest request = new HttpRequest(in);

        Assertions.assertThat(request.getMethod().isPost()).isTrue();
        Assertions.assertThat(request.getPath()).isEqualTo("/user/create");
        Assertions.assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        Assertions.assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }
}
