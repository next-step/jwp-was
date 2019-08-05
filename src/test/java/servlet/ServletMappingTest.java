package servlet;

import static org.assertj.core.api.Assertions.assertThat;

import http.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServletMappingTest {

  @Test
  @DisplayName("path와 servlet 클래스를 맵핑해준다")
  void urlPathServletMapping() {
    String path ="/user/create";

    assertThat(ServletMapping.getServlet(path)).isInstanceOf(UserServlet.class);
  }

  @Test
  @DisplayName("mapping에 등록되지 않은 path는 defaultservlet 클래스를 맵핑해준다")
  void urlPathServletMappingDefault() {
    String path ="/no/mapping";

    assertThat(ServletMapping.getServlet(path)).isInstanceOf(DefaultServlet.class);
  }

}