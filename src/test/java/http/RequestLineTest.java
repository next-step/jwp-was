package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RequestLineTest {

  RequestLine requestLine;

  @BeforeEach
  void 생성() {
    requestLine = RequestLine
        .parse("GET /users?userId=javajigi&password=password&name=JaeSung&noValue= HTTP/1.1");
  }

  @Test
  void 메소드확인() {
    assertThat(requestLine.getMethod()).isEqualTo("GET");
  }

  @Test
  void URl확인() {
    assertThat(requestLine.getPath()).isEqualTo("/users");
  }

  @Test
  void version확인() {
    assertThat(requestLine.getVersion()).isEqualTo("HTTP/1.1");
  }

  @ParameterizedTest
  @CsvSource({"userId,javajigi", "password,password", "name,JaeSung"})
  void 파라미터확인(String key, String value) {
    assertThat(requestLine.getParam(key)).isEqualTo(value);
  }

}
