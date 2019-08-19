package servlet;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.ServletMapper;

class ServletMapperTest {

  @Test
  @DisplayName("path와 servlet 클래스를 맵핑해준다")
  void urlPathServletMapping() {
    Map<String, HttpServlet> servletMappingInfo = new HashMap<>();
    servletMappingInfo.put("/user/create", new UserCreateServlet());

    ServletMapper servletMapper = new ServletMapper(servletMappingInfo);

    assertThat(servletMapper.getServlet("/user/create")).isInstanceOf(UserCreateServlet.class);
  }

  @Test
  @DisplayName("mapping에 등록되지 않은 path는 defaultservlet 클래스를 맵핑해준다")
  void urlPathServletMappingDefault() {
    Map<String, HttpServlet> servletMappingInfo = new HashMap<>();
    servletMappingInfo.put("/user/create", new UserCreateServlet());

    ServletMapper servletMapper = new ServletMapper(servletMappingInfo);

    assertThat(servletMapper.getServlet("/user/create")).isInstanceOf(UserCreateServlet.class);
  }

  @ParameterizedTest
  @ValueSource(strings = {"/css/style.css", "/js/scripts.js"})
  @DisplayName("staticResource는 StaticResourceServlet 클래스를 맵핑해준다")
  void staticResourceMappingStaticResourceServlet(String path) {
    ServletMapper servletMapper = new ServletMapper();

    assertThat(servletMapper.getServlet(path)).isInstanceOf(StaticResourceServlet.class);
  }

}