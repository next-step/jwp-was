package servlet;

import http.Parameters;
import http.RequestLine;
import model.User;

public class UserServlet implements HttpServlet {

  @Override
  public String service(RequestLine requestLine) {
    if ("/create/user".equals(requestLine.getPath())) {
      createUser(requestLine.getParameters());
    }
    return "/index.html";
  }

  User createUser(Parameters parameters) {
    return new User(parameters.getParameter("userId"), parameters.getParameter("password"),
        parameters.getParameter("name"), parameters.getParameter("email"));
  }
}
