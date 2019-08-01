package webserver.http;

import model.RequestLine;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : yusik
 * @date : 2019-08-01
 */
public class RequestLineTest {

    @Test
    void parse() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
    }
}
