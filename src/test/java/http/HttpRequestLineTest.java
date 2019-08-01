package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class HttpRequestLineTest {

  @Test
  void parse() {
    RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");
    assertThat(requestLine.getMethod()).isEqualTo("GET");
    assertThat(requestLine.getPath()).isEqualTo("/users");
  }
}
