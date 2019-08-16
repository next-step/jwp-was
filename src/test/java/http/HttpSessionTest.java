package http;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionTest {

  @Test
  @DisplayName("세션 생성확인")
  void generate() {
    UUID uuid = UUID.randomUUID();

    HttpSession httpSession = new HttpSession(uuid);
    assertThat(httpSession.getId()).isEqualTo(uuid.toString());
  }

  @Test
  @DisplayName("세션 addtibute값 확인")
  void attributeGetSet() {
    UUID uuid = UUID.randomUUID();

    HttpSession httpSession = new HttpSession(uuid);

    httpSession.setAttribute("name", "changjun");
    httpSession.setAttribute("age", 31);

    assertThat(httpSession.getAttribute("name")).isEqualTo("changjun");
    assertThat(httpSession.getAttribute("age")).isEqualTo(31);
  }

  @Test
  @DisplayName("attribute에 키값이 없으면 null을 리턴한다")
  void attributeHasNokey() {
    UUID uuid = UUID.randomUUID();

    HttpSession httpSession = new HttpSession(uuid);

    httpSession.setAttribute("name", "changjun");
    assertThat(httpSession.getAttribute("age")).isEqualTo(null);
  }

  @Test
  @DisplayName("해당 키값으로 저장된 값을 삭제한다")
  void removeAttribute() {
    UUID uuid = UUID.randomUUID();

    HttpSession httpSession = new HttpSession(uuid);

    httpSession.setAttribute("name", "changjun");
    assertThat(httpSession.getAttribute("name")).isEqualTo("changjun");
    httpSession.removeAttribute("name");
    assertThat(httpSession.getAttribute("name")).isEqualTo(null);
  }

  @Test
  @DisplayName("현재 세션에 저장된 값을 모두 삭제한다")
  void invalidate() {
    UUID uuid = UUID.randomUUID();

    HttpSession httpSession = new HttpSession(uuid);

    httpSession.setAttribute("name", "changjun");
    httpSession.setAttribute("age", 31);

    httpSession.invalidate();

    assertThat(httpSession.getAttribute("name")).isEqualTo(null);
    assertThat(httpSession.getAttribute("age")).isEqualTo(null);
  }

}