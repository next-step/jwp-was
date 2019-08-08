package servlet;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}