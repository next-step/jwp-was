//package http;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import static org.assertj.core.api.Assertions.*;
//
//public class RequestLineParserTest {
//
//    @Test
//    void parse_GET_method_requestLine() {
//        // given
//        final String method = "GET";
//        final String path = "/";
//        final String protocol = "HTTP";
//        final String version = "1.1";
//
//        String httpRawText = method + " " + path + " " + protocol + "/" + version + "\n" +
//                "Host: 127.0.0.1:8080\n" +
//                "Connection: keep-alive\n" +
//                "Cache-Control: max-age=0\n" +
//                "Upgrade-Insecure-Requests: 1\n" +
//                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36\n" +
//                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
//                "Accept-Encoding: gzip, deflate, br\n" +
//                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7";
//
//        // when
//        RequestLine req = RequestLineParser.parse(httpRawText);
//
//        // then
//        assertThat(req .getMethod()).isEqualTo(method);
//        assertThat(req .getPath()).isEqualTo(path);
//        assertThat(req .getProtocol()).isEqualTo(protocol);
//        assertThat(req .getVersion()).isEqualTo(version);
//    }
//
//    @Test
//    void parse_GET_method_requestLine_isEmpty() {
//        // given
//        String httpRawText = "";
//
//        // when
//        RequestLine httpProtocol = RequestLineParser.parse(httpRawText);
//
//        // then
//        assertThat(httpProtocol).extracting("class").isEqualTo(RequestLine.EmptyRequsetLine.class);
//    }
//
//    @Test
//    void parse_POST_method_requestLine() {
//        // given
//        final String method = "POST";
//        final String path = "/";
//        final String protocol = "HTTP";
//        final String version = "1.1";
//
//        String httpRawText = method + " " + path + " " + protocol + "/" + version + "\n" +
//                "Host: 127.0.0.1:8080\n" +
//                "Connection: keep-alive\n" +
//                "Cache-Control: max-age=0\n" +
//                "Upgrade-Insecure-Requests: 1\n" +
//                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36\n" +
//                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
//                "Accept-Encoding: gzip, deflate, br\n" +
//                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7";
//
//        // when
//        RequestLine req = RequestLineParser.parse(httpRawText);
//
//        // then
//        assertThat(req .getMethod()).isEqualTo(method);
//        assertThat(req .getPath()).isEqualTo(path);
//        assertThat(req .getProtocol()).isEqualTo(protocol);
//        assertThat(req .getVersion()).isEqualTo(version);
//    }
//
//}
