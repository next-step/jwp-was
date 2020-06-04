package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("raw http request 를 HttpRequest 객체로 파싱해주는 클래스")
class HttpRequestParserTest {

    private static final String RAW_REQUEST_STR =
            "GET /foo/bar?zoo=xoo HTTP/1.1\n" +
            "Host: example.org\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; fr; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8\n" +
            "Accept: */*\n" +
            "Accept-Language: fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3\n" +
            "Accept-Encoding: gzip,deflate\n" +
            "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7\n" +
            "Keep-Alive: 115\n" +
            "Connection: keep-alive\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "X-Requested-With: XMLHttpRequest\n" +
            "Referer: http://example.org/test\n" +
            "Cookie: foo=bar; lorem=ipsum;";

    @Test
    void parse() {
        HttpRequest httpRequest = HttpRequestParser.parse(RAW_REQUEST_STR);

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/foo/bar");
        assertThat(httpRequest.getParameter("zoo")).isEqualTo("xoo");
        assertThat(httpRequest.getProtocol()).isEqualTo("HTTP");
        assertThat(httpRequest.getVersion()).isEqualTo("1.1");
    }
}