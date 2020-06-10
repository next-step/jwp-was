package utils;

import http.HeaderProperty;
import http.HttpHeaders;
import http.HttpMethod;
import http.MediaType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpStringBuilderTest {

    @Test
    public void buildHeaderTest() {
        String result = "Content-Type: application/json\r\n" +
                "Cookie: name=value\r\n" +
                System.lineSeparator();

        String httpHeaderString = HttpStringBuilder.builder()
                .addHeader(HeaderProperty.CONTENT_TYPE.getValue(), MediaType.APPLICATION_JSON.getValue())
                .addHeader(HeaderProperty.COOKIE.getValue(), "name=value")
                .buildHeaders();

        assertThat(httpHeaderString).isEqualTo(result);
    }

    @Test
    public void buildRequestTest() {
        String result = "POST /users/create HTTP/1.1\r\n" +
                "Content-Type: application/json\r\n" +
                "Cookie: name=value\r\n" +
                System.lineSeparator() +
                "userId=1&name=KingCjy&status=ACTIVE,INACTIVE&age=";

        String httpString = HttpStringBuilder.builder()
                .httpMethod(HttpMethod.POST)
                .path("/users/create")
                .addHeader(HeaderProperty.CONTENT_TYPE.getValue(), MediaType.APPLICATION_JSON.getValue())
                .addHeader(HeaderProperty.COOKIE.getValue(), "name=value")
                .body("userId=1&name=KingCjy&status=ACTIVE,INACTIVE&age=")
                .buildRequest();

        assertThat(httpString).isEqualTo(result);
    }

    @Test
    public void buildResponseTest() {

        String result = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "Set-Cookie: _ga=GA1.1.418509108.1586868854\r\n" +
                System.lineSeparator() +
                "userId=1&name=KingCjy&status=ACTIVE,INACTIVE&age=";

        String httpString = HttpStringBuilder.builder()
                .path("/users")
                .addHeader(HeaderProperty.CONTENT_TYPE.getValue(), MediaType.APPLICATION_JSON.getValue())
                .addHeader(HeaderProperty.SET_COOKIE.getValue(), "_ga=GA1.1.418509108.1586868854")
                .body("userId=1&name=KingCjy&status=ACTIVE,INACTIVE&age=")
                .buildResponse();

        assertThat(httpString).isEqualTo(result);
    }
}
