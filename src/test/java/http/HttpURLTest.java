package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpURLTest {

  @Test
  @DisplayName("URL을 파싱한다")
  void urlParse() {
    HttpURL url = HttpURL.parse("/users?userId=javajigi&password=password&name=JaeSung");

    assertThat(url.getPath()).isEqualTo("/users");
  }

  @Test
  @DisplayName("queryStrig이_없을때는_빈객체가_생성된다")
  void emptyQueryString() {
    HttpURL url = HttpURL.parse("/users");

    assertThat(url.getPath()).isEqualTo("/users");
  }
}