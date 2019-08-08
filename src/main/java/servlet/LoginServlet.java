package servlet;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import java.util.Map;
import model.User;

public class LoginServlet extends AbstractHttpServlet {

  @Override
  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (isLogin(httpRequest.getParameters())) {
      httpResponse.addLoginCookie();
      httpResponse.forward("/index.html");
    }
    httpResponse.sendRedirect("/user/login_failed.html");
  }

  private boolean isLogin(Map<String, String> parameters) {
    User user = DataBase.findUserById(parameters.get("userId"));
    return user != null && user.isCorrectPassword(parameters.get("password"));
  }
}
