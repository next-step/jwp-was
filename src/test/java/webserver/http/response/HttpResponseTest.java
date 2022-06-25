package webserver.http.response;

import org.junit.jupiter.api.Test;
import webserver.http.Cookie;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @Test
    void Redirect_응답() throws IOException {
        // given
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        // when
        httpResponse.responseRedirect("/index.html");

        // then
        final String rawHttpResponse = byteArrayOutputStream.toString();
        assertThat(rawHttpResponse)
                .isEqualTo("HTTP/1.1 302 Found \r\n" +
                        "Location: /index.html\r\n"
                );
    }

    @Test
    void Cookie가_설정된_Redirect_응답() throws IOException {
        // given
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        // when
        httpResponse.setCookie(new Cookie("logined", "true"));
        httpResponse.responseRedirect("/index.html");

        // then
        final String rawHttpResponse = byteArrayOutputStream.toString();
        assertThat(rawHttpResponse)
                .isEqualTo("HTTP/1.1 302 Found \r\n" +
                        "Location: /index.html\r\n" +
                        "Set-Cookie: logined=true; Path=/\r\n" +
                        "\r\n"
                );
    }

    @Test
    void Not_Found_응답() throws IOException {
        // given
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        // when
        httpResponse.responseNotFound();

        // then
        final String rawHttpResponse = byteArrayOutputStream.toString();
        assertThat(rawHttpResponse)
                .isEqualTo("HTTP/1.1 404 Not Found \r\n");
    }

    @Test
    void Method_Not_Allowed_응답() throws IOException {
        // given
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        // when
        httpResponse.responseMethodNotAllowed();

        // then
        final String rawHttpResponse = byteArrayOutputStream.toString();
        assertThat(rawHttpResponse)
                .isEqualTo("HTTP/1.1 405 Method Not Allowed \r\n");
    }
}
