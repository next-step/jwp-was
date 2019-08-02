package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RequestLineTest {

  RequestLine requestLine;

  @BeforeEach
  void 생성() {
    requestLine = RequestLine.parse("GET /users HTTP/1.1");
  }

  @Test
  void 메소드확인() {
    assertThat(requestLine.getMethod()).isEqualTo("GET");
  }

  @Test
  void URI확인() {
    assertThat(requestLine.getUri()).isEqualTo("/users");
  }

  @Test
  void version확인() {
    assertThat(requestLine.getVersion()).isEqualTo("HTTP/1.1");
  }

}
