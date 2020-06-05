package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.StringTokenizer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("리퀘스트 스트링 클래스")
class RawRequestTest {
    public static final String REQUEST_LINE = "GET /foo/bar?zoo=xoo HTTP/1.1\n";
    public static final String HEADER =
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
                    "Cookie: foo=bar; lorem=ipsum;\n" +
                    "\n";
    public static final String BODY = "some body contents\n";

    public static final String RAW_REQUEST_STR = REQUEST_LINE + HEADER + BODY;

    @Test
    @DisplayName("리퀘스트 스트링을 파싱하면, 리퀘스트 라인, 헤더, 바디를 나눈 스트링으로 나눈다.")
    void parse() {
        RawRequest rawRequest = new RawRequest(RAW_REQUEST_STR);

        assertThat(rawRequest.getRequestLine()).isEqualTo(REQUEST_LINE.replace("\n", ""));
        assertThat(rawRequest.getHeaders()).isEqualTo(HEADER.substring(0, HEADER.length() - 1));
        assertThat(rawRequest.getBody()).isEqualTo(BODY);
    }

    @Test
    @DisplayName("split test")
    void splitTest() {
        String[] tokens = RAW_REQUEST_STR.split("\n");

        System.out.println("START");
        Arrays.stream(tokens)
                .forEach(System.out::println);
        System.out.println("END");
    }

    @Test
    @DisplayName("tokenizer test")
    void tokenizerTest() {
        StringTokenizer stringTokenizer = new StringTokenizer(RAW_REQUEST_STR, "\n");

        System.out.println("START");
        while (stringTokenizer.hasMoreTokens()) {
            System.out.println(stringTokenizer.nextToken());
        }
        System.out.println("END");
    }
}