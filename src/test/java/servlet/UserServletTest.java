package servlet;

import static org.assertj.core.api.Assertions.assertThat;

import http.RequestLine;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServletTest {

  @Test
  @DisplayName("유저 생성")
  void createUser() {
  /*  RequestLine requestLine = RequestLine.parse(
        "POST /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");
    UserServlet userServlet = new UserServlet();
    User user = userServlet.createUser(requestLine.getParameters());

    String userId = "javajigi";
    String password = "password";
    String name = "%EB%B0%95%EC%9E%AC%EC%84%B1";
    String email = "javajigi%40slipp.net";

    assertThat(user).isEqualTo(new User(userId, password, name, email));*/
  }

}