package servlet;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import java.util.Map;
import model.User;
import view.RedirectView;
import view.View;

public class LoginServlet extends AbstractHttpServlet {

  private static final String LOGIN_SESSION_KEY = "logined";

  @Override
  public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (isLoginSuccess(httpRequest.getParameters())) {
      HttpSession httpSession = httpRequest.getHttpSession();
      httpSession.setAttribute(LOGIN_SESSION_KEY, true);
      return new RedirectView("/index.html");
    }
    return new RedirectView("/user/login_failed.html");
  }

  private boolean isLoginSuccess(Map<String, String> parameters) {
    User user = DataBase.findUserById(parameters.get("userId"));
    return user != null && user.isCorrectPassword(parameters.get("password"));
  }

}
