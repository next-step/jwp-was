package servlet;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import http.Parameters;
import java.util.Map;
import model.User;

public class UserCreateServlet extends AbstractHttpServlet {

  @Override
  public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    User user = createUser(httpRequest.getParameters());
    DataBase.addUser(user);

    httpResponse.setHttpVersion("HTTP1/1");
    httpResponse.setHttpStatus(HttpStatus.Found);
    httpResponse.setContentType("Content-Type: text/html;charset=utf-8");
    httpResponse.setLocation("/index.html");

    httpResponse.render();
  }

  User createUser(Map<String, String> parameters) {
    return new User(parameters.get("userId"), parameters.get("password"),
        parameters.get("name"), parameters.get("email"));
  }

}
