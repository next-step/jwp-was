package webserver;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.parser.RequestReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("GET 메서드의 Http Request Message를 정상적으로 파싱할 수 있다")
    public void requestGET() throws Exception {
        final InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        final HttpRequest request = RequestReader.read(in);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).hasValue("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }

    @Test
    @DisplayName("POST 메서드의 Http Request Message를 정상적으로 파싱할 수 있다")
    public void requestPOST() throws Exception {
        final InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        final HttpRequest request = RequestReader.read(in);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).hasValue("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }
}