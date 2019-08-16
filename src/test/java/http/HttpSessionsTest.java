package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionsTest {

  @Test
  @DisplayName("새로운 세션 추가 후 세션 키를 반환한다")
  void addHttpSession() {
    HttpSessions httpSessions = new HttpSessions();
    String sessionKey = httpSessions.createNewSession();

    assertThat(httpSessions.get(sessionKey)).isInstanceOf(HttpSession.class);
  }

  @Test
  @DisplayName("해당하는 sessionkey가 있는지 확인한다")
  void hasHttpSessionKey() {
    HttpSessions httpSessions = new HttpSessions();
    String sessionKey = httpSessions.createNewSession();

    assertThat(httpSessions.containsKey(sessionKey)).isTrue();
    assertThat(httpSessions.containsKey("333")).isFalse();
  }
}