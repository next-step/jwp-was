package servlet;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.Parameters;
import model.User;

public class UserCreateServlet implements HttpServlet {

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
    User user = createUser(httpRequest.getParameters());
    System.out.println(user.toString());
    DataBase.addUser(user);
    httpResponse.sendRedirect("/index.html");
  }

  User createUser(Parameters parameters) {
    return new User(parameters.getParameter("userId"), parameters.getParameter("password"),
        parameters.getParameter("name"), parameters.getParameter("email"));
  }
}
