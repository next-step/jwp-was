package servlet;

import static org.assertj.core.api.Assertions.assertThat;

import http.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServletMappingTest {

  @Test
  @DisplayName("path와 servlet 클래스를 맵핑해준다")
  void urlPathServletMapping() {
    RequestLine requestLine = RequestLine.parse(
        "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");

    assertThat(ServletMapping.getServlet(requestLine)).isInstanceOf(UserServlet.class);
  }

  @Test
  @DisplayName("mapping에 등록되지 않은 path는 defaultservlet 클래스를 맵핑해준다")
  void urlPathServletMappingDefault() {
    RequestLine requestLine = RequestLine.parse("GET /no/mapping HTTP/1.1");
    assertThat(ServletMapping.getServlet(requestLine)).isInstanceOf(DefaultServlet.class);
  }

}