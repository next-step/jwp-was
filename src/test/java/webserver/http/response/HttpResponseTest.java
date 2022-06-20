package webserver.http.response;

import org.junit.jupiter.api.Test;
import webserver.http.Cookie;
import webserver.http.response.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {
    @Test
    void HTML_템플릿_응답() throws IOException, URISyntaxException {
        // given
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        // when
        httpResponse.setBodyContentPath("/test.html");
        httpResponse.responseOK();

        // then
        final String rawHttpResponse = byteArrayOutputStream.toString();
        assertThat(rawHttpResponse)
                .isEqualTo("HTTP/1.1 200 OK \r\n" +
                        "Content-Length: 27\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "\r\n" +
                        "<html><h1>test</h1></html>\n"
                );
    }

    @Test
    void CSS_응답() throws IOException, URISyntaxException {
        // given
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        // when
        httpResponse.setBodyContentPath("/css/test.css");
        httpResponse.responseOK();

        // then
        final String rawHttpResponse = byteArrayOutputStream.toString();
        assertThat(rawHttpResponse)
                .isEqualTo("HTTP/1.1 200 OK \r\n" +
                        "Content-Length: 19\r\n" +
                        "Content-Type: text/css;charset=utf-8\r\n" +
                        "\r\n" +
                        "h1 { color: red; }\n"
                );
    }

    @Test
    void JS_응답() throws IOException, URISyntaxException {
        // given
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        // when
        httpResponse.setBodyContentPath("/js/test.js");
        httpResponse.responseOK();

        // then
        final String rawHttpResponse = byteArrayOutputStream.toString();
        assertThat(rawHttpResponse)
                .isEqualTo("HTTP/1.1 200 OK \r\n" +
                        "Content-Length: 21\r\n" +
                        "Content-Type: text/javascript;charset=utf-8\r\n" +
                        "\r\n" +
                        "console.log(\"test\");\n"
                );
    }

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
