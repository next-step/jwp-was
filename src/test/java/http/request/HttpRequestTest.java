package http.request;

import http.Statics;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testutils.HttpRequestGenerator;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("요청에 대한 객체")
public class HttpRequestTest {

    @Test
    void parse() throws IOException {
        HttpRequest httpRequest = HttpRequestGenerator.init("GET /users?name1=value1&name2=value2 HTTP/1.1");

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/users");
        assertThat(httpRequest.getParameter("name1")).isEqualTo("value1");
        assertThat(httpRequest.getParameter("name2")).isEqualTo("value2");
        assertThat(httpRequest.getProtocol()).isEqualTo("HTTP");
        assertThat(httpRequest.getVersion()).isEqualTo("1.1");
    }

    @Test
    void init() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Statics.RAW_REQUEST_STR.getBytes());
        HttpRequest httpRequest = HttpRequest.readRawRequest(inputStream);

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/foo/bar");
        assertThat(httpRequest.getParameter("zoo")).isEqualTo("xoo");
        assertThat(httpRequest.getProtocol()).isEqualTo("HTTP");
        assertThat(httpRequest.getVersion()).isEqualTo("1.1");
        assertThat(httpRequest.getCookie("foo")).isEqualTo("bar");
        assertThat(httpRequest.getCookie("lorem")).isEqualTo("ipsum");
        assertThat(httpRequest.getExtension()).isNull();
        assertThat(httpRequest.getParameter("x")).isEqualTo("1");
        assertThat(httpRequest.getParameter("y")).isEqualTo("2");
        assertThat(httpRequest.getParameter("z")).isEqualTo("3");
        assertThat(httpRequest.getHeader("Keep-Alive")).isEqualTo("115");
    }
}
