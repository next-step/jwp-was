package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("리퀘스트 해더들을 관리하는 class")
class RequestHeaderTest {

//    "Host: example.org\n" +
//    "User-Agent: Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; fr; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8\n" +
//    "Accept: */*\n" +
//    "Accept-Language: fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3\n" +
//    "Accept-Encoding: gzip,deflate\n" +
//    "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7\n" +
//    "Keep-Alive: 115\n" +
//    "Connection: keep-alive\n" +
//    "Content-Type: application/x-www-form-urlencoded\n" +
//    "X-Requested-With: XMLHttpRequest\n" +
//    "Referer: http://example.org/test\n" +
//    "Cookie: foo=bar; lorem=ipsum;\n" +
    @Test
    @DisplayName("리퀘스트 헤더들을 받으면 key, value로 나눠준다.")
    void parse() {
        String header = RawRequestTest.HEADER;

        RequestHeader requestHeader = new RequestHeader(header);

        assertThat(requestHeader.getHeader("Host")).isEqualTo("example.org");
        assertThat(requestHeader.getHeader("Keep-Alive")).isEqualTo("115");
        assertThat(requestHeader.getHeader("not exist")).isNull();
        assertThat(requestHeader.getCookie("foo")).isEqualTo("bar");
        assertThat(requestHeader.getCookie("lorem")).isEqualTo("ipsum");
        assertThat(requestHeader.getHeaders()).hasSize(11);
    }
}