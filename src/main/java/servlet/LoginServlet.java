package servlet;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.Parameters;
import java.util.Map;
import model.User;

public class LoginServlet extends AbstractHttpServlet {

  @Override
  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (login(httpRequest.getParameters())) {
      httpResponse.addLoginCookie();
      httpResponse.forward("/index.html");
    }
    httpResponse.sendRedirect("/user/login_failed.html");
  }

  private boolean login(Map<String, String> parameters) {
    User userId = DataBase.findUserById(parameters.get("userId"));
    return userId != null && userId.getPassword().equals(parameters.get("password"));
  }
}
