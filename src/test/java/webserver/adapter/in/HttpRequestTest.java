package webserver.adapter.in;

import org.junit.jupiter.api.Test;
import webserver.domain.http.HttpMethod;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");

        HttpRequest actual = new HttpRequest(in);

        assertThat(actual.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(actual.getUri().getPath()).isEqualTo("/user/create");
        assertThat(actual.getHttpHeader().get("Connection")).isEqualTo("keep-alive");
        assertThat(actual.getUri().getQueryString().get("userId")).isEqualTo("javajigi");
    }

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_POST.txt");

        HttpRequest actual = new HttpRequest(in);

        assertThat(actual.getHttpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(actual.getUri().getPath()).isEqualTo("/user/create");
        assertThat(actual.getHttpHeader().get("Connection")).isEqualTo("keep-alive");
        assertThat(actual.getRequestBody().get("userId")).isEqualTo("javajigi");
    }

    @Test
    public void request_POST2() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_POST2.txt");

        HttpRequest actual = new HttpRequest(in);

        assertThat(actual.getHttpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(actual.getUri().getPath()).isEqualTo("/user/create");
        assertThat(actual.getHttpHeader().get("Connection")).isEqualTo("keep-alive");
        assertThat(actual.getUri().getQueryString().get("id")).isEqualTo("1");
        assertThat(actual.getRequestBody().get("userId")).isEqualTo("javajigi");
    }
}
