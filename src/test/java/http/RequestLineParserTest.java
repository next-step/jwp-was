package http;

import http.request.Protocol;
import http.request.RequestLine;
import http.request.RequestLineParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static http.request.HttpMethod.GET;
import static http.request.HttpMethod.POST;
import static org.assertj.core.api.Assertions.assertThat;


public class RequestLineParserTest {

    @DisplayName("GET요청에 대한 RequestLine을 파싱하여 저장한다")
    @Test
    void parseGetMethod() {
        RequestLine line = RequestLineParser.parse("GET /users HTTP/1.1");
        assertThat(line.getMethod()).isEqualTo(GET);
        assertThat(line.getPath()).isEqualTo("/users");
        assertThat(line.getProtocol()).isEqualTo(new Protocol("HTTP", "1.1"));
    }

    @DisplayName("POST요청에 대한 RequestLine을 파싱하여 저장한다")
    @Test
    void parsePostMethod() {
        RequestLine line = RequestLineParser.parse("POST /users HTTP/1.1");
        assertThat(line.getMethod()).isEqualTo(POST);
        assertThat(line.getPath()).isEqualTo("/users");
        assertThat(line.getProtocol()).isEqualTo(new Protocol("HTTP", "1.1"));
    }

}
