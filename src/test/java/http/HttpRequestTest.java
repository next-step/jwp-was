package http;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private HttpRequest sut;

    @Test
    void new_httpRequest_bodyExist() throws IOException {
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
    void new_httpRequest() throws IOException {
        // given
        String keepAlive = "keep-alive";
        String bodyString = "bodyString";
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

        // when
        sut = HttpRequest.from(inputStream);
        String connectionHeaderValue = sut.getHeaders().get("Connection");

        // then
        assertThat(connectionHeaderValue).isEqualTo(keepAlive);

    }

}