package servlet;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.Parameters;
import model.User;

public class LoginServlet extends AbstractHttpServlet {

  @Override
  public void action(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (login(httpRequest.getParameters())) {
      httpResponse.addLoginCookie();
      httpResponse.forward("/index.html");
    }
    httpResponse.sendRedirect("/user/login_failed.html");
  }

  private boolean login(Parameters parameters) {
    User userId = DataBase.findUserById(parameters.getParameter("userId"));
    return userId != null && userId.getPassword().equals(parameters.getParameter("password"));
  }

}
