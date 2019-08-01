package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.RequestLine;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by youngjae.havi on 2019-08-01
 */
public class RequestLineTest {

    @Test
    void parse() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
    }
}
