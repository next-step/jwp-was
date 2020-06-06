package http;

import org.junit.jupiter.api.Test;

import static http.HttpMethod.GET;
import static http.HttpMethod.POST;
import static org.assertj.core.api.Assertions.assertThat;


public class RequestLineParserTest {

    @Test
    void parseGetMethod() {
        RequestLine line = RequestLineParser.parse("GET /users HTTP/1.1");
        assertThat(line.getMethod()).isEqualTo(GET);
        assertThat(line.getPath()).isEqualTo(new Path("/users"));
        assertThat(line.getProtocol()).isEqualTo(new Protocol("HTTP", "1.1"));
    }

    @Test
    void parsePostMethod() {
        RequestLine line = RequestLineParser.parse("POST /users HTTP/1.1");
        assertThat(line.getMethod()).isEqualTo(POST);
        assertThat(line.getPath()).isEqualTo(new Path("/users"));
        assertThat(line.getProtocol()).isEqualTo(new Protocol("HTTP", "1.1"));
    }

}
