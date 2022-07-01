package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.WasBaseTest;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest extends WasBaseTest {
    @Test
    public void request_GET_without_QueryString() throws Exception {
        HttpRequest request = request("http/HTTP_GET_without_QueryString.txt");

        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/users");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
    }

    @Test
    public void request_GET_with_QueryString() throws Exception {
        HttpRequest request = request("http/HTTP_GET_with_QueryString.txt");

        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/users");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }

    @Test
    public void request_POST_with_body1() throws Exception {
        HttpRequest request = request("http/HTTP_POST_with_body1.txt");

        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/users");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }

    @Test
    public void request_POST_with_body2() throws Exception {
        HttpRequest request = request("http/HTTP_POST_with_body2.txt");

        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/users");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("id")).isEqualTo("1");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }
}
