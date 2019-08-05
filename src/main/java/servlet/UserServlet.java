package servlet;

import db.DataBase;
import http.HttpRequest;
import http.Parameters;
import model.User;

public class UserServlet implements HttpServlet {

  @Override
  public String service(HttpRequest httpRequest) {
    if (httpRequest.isPost()) {
      return doPost(httpRequest);
    }
    return doGet(httpRequest);
  }

  @Override
  public String doGet(HttpRequest httpRequest) {
    return null;
  }

  @Override
  public String doPost(HttpRequest httpRequest) {
    if ("/user/create".equals(httpRequest.getPath())) {
      User user = createUser(httpRequest.getParameters());
      System.out.println(user.toString());
      DataBase.addUser(user);
    }
    return "/index.html";
  }

  User createUser(Parameters parameters) {
    return new User(parameters.getParameter("userId"), parameters.getParameter("password"),
        parameters.getParameter("name"), parameters.getParameter("email"));
  }
}
