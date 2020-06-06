package http;

import org.junit.jupiter.api.Test;
import org.springframework.util.MultiValueMap;
import utils.HttpStringBuilder;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {

    @Test
    public void setHeaderTest() {
        MultiValueMap<String, String> testHeaders = HttpHeaders.emptyHeaders();
        testHeaders.add(HttpHeaders.USER_AGENT, "Chrome/1.1");
        testHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

        HttpResponse httpResponse = HttpResponse.from(new DataOutputStream(new ByteArrayOutputStream()));
        httpResponse.setContentType(MediaType.APPLICATION_JSON);
        httpResponse.setHeader(HttpHeaders.USER_AGENT, "Chrome/1.1");

        assertThat(httpResponse.getHeaderMap()).isEqualTo(testHeaders);
    }

    @Test
    public void writeHeaderTest() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        HttpStringBuilder builder = HttpStringBuilder.builder()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .addHeader(HttpHeaders.ACCEPT, MediaType.TEXT_HTML)
                .addHeader(HttpHeaders.USER_AGENT, "Chrome/1.1");

        HttpResponse httpResponse = HttpResponse.from(new DataOutputStream(byteArrayOutputStream));
        httpResponse.setHeaders(HttpHeaders.from(builder.buildHeaders()));

        String httpString = builder.buildResponse();

        httpResponse.writeHeader();;
        httpResponse.getDataOutputStream().flush();

        String httpResult = new String(byteArrayOutputStream.toByteArray());

        assertThat(httpResult).isEqualTo(httpString);
    }

    @Test
    public void sendRedirectTest() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        HttpResponse httpResponse = HttpResponse.from(new DataOutputStream(byteArrayOutputStream));
        httpResponse.sendRedirect("/test_view.html");

        String result = HttpStringBuilder.builder()
                .httpStatus(HttpStatus.FOUND)
                .addHeader(HttpHeaders.LOCATION, "/test_view.html")
                .buildResponse();

        String httpResult = new String(byteArrayOutputStream.toByteArray());

        assertThat(httpResult).isEqualTo(result);
    }

    @Test
    public void addCookieTest() {
        String resultString = "name=value; Path=/";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.from(new DataOutputStream(byteArrayOutputStream));

        httpResponse.addCookie(new Cookie("name", "value"));

        assertThat(httpResponse.getHeader(HttpHeaders.SET_COOKIE)).isEqualTo(resultString);
        assertThat(httpResponse.getCookies()).contains(new Cookie("name", "value"));
    }
}
