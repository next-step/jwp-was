package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HttpURLTest {

  HttpURL url;

  @BeforeEach
  void 생성() {
    url = HttpURL.parse("/users?userId=javajigi&password=password&name=JaeSung");
  }

  @Test
  void path확인() {
    assertThat(url.getPath()).isEqualTo("/users");
  }

  @Test
  void queryStrig이_없을때는_빈객체가_생성된다() {
    HttpURL url = HttpURL.parse("/users");

    assertThat(url.getPath()).isEqualTo("/users");
  }
}