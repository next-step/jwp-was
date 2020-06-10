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
        testHeaders.add(HeaderProperty.USER_AGENT.getValue(), "Chrome/1.1");
        testHeaders.add(HeaderProperty.CONTENT_TYPE.getValue(), MediaType.APPLICATION_JSON.getValue());

        HttpResponse httpResponse = HttpResponse.from(new DataOutputStream(new ByteArrayOutputStream()));
        httpResponse.setContentType(MediaType.APPLICATION_JSON.getValue());
        httpResponse.setHeader(HeaderProperty.USER_AGENT.getValue(), "Chrome/1.1");

        assertThat(httpResponse.getHeaderMap()).isEqualTo(testHeaders);
    }

    @Test
    public void writeHeaderTest() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        HttpStringBuilder builder = HttpStringBuilder.builder()
                .addHeader(HeaderProperty.CONTENT_TYPE.getValue(), MediaType.APPLICATION_JSON.getValue())
                .addHeader(HeaderProperty.ACCEPT.getValue(), MediaType.TEXT_HTML.getValue())
                .addHeader(HeaderProperty.USER_AGENT.getValue(), "Chrome/1.1");

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
                .addHeader(HeaderProperty.LOCATION.getValue(), "/test_view.html")
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

        assertThat(httpResponse.getHeader(HeaderProperty.SET_COOKIE.getValue())).isEqualTo(resultString);
        assertThat(httpResponse.getCookies()).contains(new Cookie("name", "value"));
    }
}
