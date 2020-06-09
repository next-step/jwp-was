package http;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpStringBuilder;

import java.io.*;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    @Test
    public void parseTest() throws IOException {
        String httpString = HttpStringBuilder.builder()
                .httpMethod(HttpMethod.GET)
                .path("/users")
                .addParameter("key", "1")
                .addHeader(HeaderProperty.ACCEPT.getValue(), MediaType.TEXT_HTML.getValue())
                .addHeader(HeaderProperty.USER_AGENT.getValue(), "Chrome/1.1")
                .buildRequest();
        BufferedReader bufferedReader = new BufferedReader(new StringReader(httpString));


        HttpRequest httpRequest = HttpRequest.from(bufferedReader);

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/users");
        assertThat(httpRequest.getHeader(HeaderProperty.ACCEPT.getValue())).isEqualTo(MediaType.TEXT_HTML.getValue());
        assertThat(httpRequest.getParameter("key")).isEqualTo("1");
    }

    @Test
    public void parseCookieTest() throws IOException {
        String httpString = HttpStringBuilder.builder()
                .path("/users")
                .addParameter("key", "1")
                .addHeader(HeaderProperty.COOKIE.getValue(), "cookie1=one; cookie2=two")
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
                .addHeader(HeaderProperty.ACCEPT.getValue(), MediaType.TEXT_HTML.getValue())
                .addHeader(HeaderProperty.CONTENT_LENGTH.getValue(), String.valueOf(body.getBytes().length))
                .body(body)
                .buildRequest();

        BufferedReader bufferedReader = new BufferedReader(new StringReader(httpString));

        HttpRequest httpRequest = HttpRequest.from(bufferedReader);

        assertThat(httpRequest.getParameter("userId")).isEqualTo("KingCjy");
        assertThat(httpRequest.getParameter("password")).isEqualTo("1234");
        assertThat(httpRequest.getParameter("key")).isEqualTo("key");
    }

    @Test
    public void getSessionTest() throws IOException {
        String httpString = HttpStringBuilder.builder()
                .addHeader(HeaderProperty.COOKIE.getValue(), "JSESSION_ID=MYSESSION")
                .buildRequest();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.from(new DataOutputStream(byteArrayOutputStream));

        HttpRequest httpRequest = HttpRequest.from(new BufferedReader(new StringReader(httpString)));
        httpRequest.linkHttpResponse(httpResponse);

        HttpSession httpSession = httpRequest.getSession();
        logger.debug("Session Created : {}", httpSession);
    }
}
