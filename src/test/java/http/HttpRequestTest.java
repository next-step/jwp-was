package http;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private HttpRequest sut;

    @Test
    void from() throws IOException {
        // given
        String keepAlive = "keep-alive";
        String bodyString = "a=1";
        String rawRequestString = "GET / HTTP/1.1\n" +
                "Host: 127.0.0.1:8080\n" +
                "Connection: " + keepAlive + "\n" +
                "Content-Length: 3" + "\n" +
                "Pragma: no-cache\n" +
                "Cache-Control: no-cache\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\r\n"
                + "\r\n"
                + bodyString;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(rawRequestString.getBytes());

        // when
        sut = HttpRequest.from(inputStream);
        String connectionHeaderValue = sut.getHeaders().get("Connection");
        String bodyValue = (String)sut.getBody().getQueryMap().get("a");

        // then
        assertThat(connectionHeaderValue).isEqualTo(keepAlive);
        assertThat(bodyValue).isEqualTo("1");
    }

    @Test
    void getHeaders() throws IOException {
        // given
        String keepAlive = "keep-alive";
        String rawRequestString = "GET / HTTP/1.1\n" +
                "Host: 127.0.0.1:8080\n" +
                "Connection: " + keepAlive + "\n" +
                "Pragma: no-cache\n" +
                "Cache-Control: no-cache\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\r\n" +
                "\r\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(rawRequestString.getBytes());
        sut = HttpRequest.from(inputStream);

        // when
        String connectionHeaderValue = sut.getHeaders().get("Connection");

        // then
        assertThat(connectionHeaderValue).isEqualTo(keepAlive);

    }

    @Test
    void getCookieValue() throws IOException {
        // given
        String cookieName1 = "cookieName1";
        String cookieName2 = "cookieName2";
        String cookieValue1 = "cookieValue1";
        String cookieValue2 = "cookieValue2";
        String rawRequestString = "GET / HTTP/1.1\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36\n" +
                "Cookie: "+ cookieName1 + "=" + cookieValue1 + "; " + cookieName2 + "=" + cookieValue2 + ";\r\n" +
                "\r\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(rawRequestString.getBytes());
        sut = HttpRequest.from(inputStream);

        // when
        String value1 = sut.getCookieValue(cookieName1);
        String value2 = sut.getCookieValue(cookieName2);

        // then
        assertThat(value1).isEqualTo(cookieValue1);
        assertThat(value2).isEqualTo(cookieValue2);
    }

    @Test
    void getCookieValue_null() throws IOException {
        // given
        String rawRequestString = "GET / HTTP/1.1\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36\n" +
                "\r\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(rawRequestString.getBytes());
        sut = HttpRequest.from(inputStream);

        // when
        String value = sut.getCookieValue("anyString");

        // then
        assertThat(value).isNull();
    }
}