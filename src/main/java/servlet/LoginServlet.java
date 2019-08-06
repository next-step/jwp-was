package servlet;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.Parameters;
import model.User;

public class LoginServlet implements HttpServlet {

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (httpRequest.isPost()) {
      doPost(httpRequest, httpResponse);
      return;
    }
    doGet(httpRequest, httpResponse);
  }

  @Override
  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

  }

  @Override
  public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (login(httpRequest.getParameters())) {
      httpResponse.addCookie("logined", "true");
      httpResponse.forward("/index.html");
    }
    httpResponse.sendRedirect("/user/login_failed.html");
  }

  private boolean login(Parameters parameters) {
    User userId = DataBase.findUserById(parameters.getParameter("userId"));
    return userId != null && userId.getPassword().equals(parameters.getParameter("password"));
  }
}
