package servlet;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.util.Map;
import model.User;

public class LoginServlet extends AbstractHttpServlet {

  private static final String LOGIN_COOKIE_KEY = "logined";

  @Override
  public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    httpResponse.setHttpVersion("HTTP1/1");
    httpResponse.setHttpStatus(HttpStatus.Found);
    httpResponse.setContentType("Content-Type: text/html;charset=utf-8");

    if (isLoginSuccess(httpRequest.getParameters())) {
      httpResponse.addCookie(LOGIN_COOKIE_KEY, "true");
      httpResponse.setLocation("/index.html");
      httpResponse.render();
      return;
    }
    httpResponse.setLocation("/user/login_failed.html");
    httpResponse.render();
  }

  private boolean isLoginSuccess(Map<String, String> parameters) {
    User user = DataBase.findUserById(parameters.get("userId"));
    return user != null && user.isCorrectPassword(parameters.get("password"));
  }

}
