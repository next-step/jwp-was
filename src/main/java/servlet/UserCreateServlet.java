package servlet;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.Parameters;
import model.User;

public class UserCreateServlet extends AbstractHttpServlet {

  @Override
  public void action(HttpRequest httpRequest, HttpResponse httpResponse) {
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
