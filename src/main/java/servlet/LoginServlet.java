package servlet;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import java.util.Map;
import model.User;
import view.View;

public class LoginServlet extends AbstractHttpServlet {

  private static final String LOGIN_COOKIE_KEY = "logined";

  @Override
  public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (isLoginSuccess(httpRequest.getParameters())) {
      httpResponse.addCookie(LOGIN_COOKIE_KEY, "true");
      httpResponse.sendRedirect("/index.html");
      return;
    }
    httpResponse.sendRedirect("/user/login_failed.html");
  }

  private boolean isLoginSuccess(Map<String, String> parameters) {
    User user = DataBase.findUserById(parameters.get("userId"));
    return user != null && user.isCorrectPassword(parameters.get("password"));
  }

}
