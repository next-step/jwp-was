package http;

import org.junit.jupiter.api.Test;
import utils.HttpStringBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    @Test
    public void parseTest() throws IOException {
        String httpString = HttpStringBuilder.builder()
                .httpMethod(HttpMethod.GET)
                .path("/users")
                .addParameter("key", "1")
                .addHeader(HttpHeaders.ACCEPT, MediaType.TEXT_HTML)
                .addHeader(HttpHeaders.USER_AGENT, "Chrome/1.1")
                .buildRequest();
        BufferedReader bufferedReader = new BufferedReader(new StringReader(httpString));


        HttpRequest httpRequest = HttpRequest.from(bufferedReader);

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/users");
        assertThat(httpRequest.getHeader(HttpHeaders.ACCEPT)).isEqualTo(MediaType.TEXT_HTML);
        assertThat(httpRequest.getParameter("key")).isEqualTo("1");
    }

    @Test
    public void parseCookieTest() throws IOException {
        String httpString = HttpStringBuilder.builder()
                .path("/users")
                .addParameter("key", "1")
                .addHeader(HttpHeaders.COOKIE, "cookie1=one; cookie2=two")
                .buildRequest();
        BufferedReader bufferedReader = new BufferedReader(new StringReader(httpString));

        HttpRequest httpRequest = HttpRequest.from(bufferedReader);
        Set<Cookie> cookies = httpRequest.getCookies();

        assertThat(cookies).contains(
                new Cookie("cookie1", "one"),
                new Cookie("cookie2", "two")
        );
    }

    @Test
    public void parseBodyTest() throws IOException {
        String body = "userId=KingCjy&password=1234";
        String httpString = HttpStringBuilder.builder()
                .httpMethod(HttpMethod.POST)
                .path("/users/create")
                .addParameter("key", "key")
                .addHeader(HttpHeaders.ACCEPT, MediaType.TEXT_HTML)
                .addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.getBytes().length))
                .body(body)
                .buildRequest();

        BufferedReader bufferedReader = new BufferedReader(new StringReader(httpString));

        HttpRequest httpRequest = HttpRequest.from(bufferedReader);

        assertThat(httpRequest.getParameter("userId")).isEqualTo("KingCjy");
        assertThat(httpRequest.getParameter("password")).isEqualTo("1234");
        assertThat(httpRequest.getParameter("key")).isEqualTo("key");
    }
}
