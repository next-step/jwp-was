package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestLineTest {

  @Test
  @DisplayName("Request Line을 파싱한다")
  void parse() {
    RequestLine requestLine = RequestLine
        .parse("GET /users?userId=javajigi&password=password&name=JaeSung&noValue= HTTP/1.1");

    assertThat(requestLine.getMethod()).isEqualTo("GET");
    assertThat(requestLine.getPath()).isEqualTo("/users");
    assertThat(requestLine.getVersion()).isEqualTo("HTTP/1.1");
  }

}
