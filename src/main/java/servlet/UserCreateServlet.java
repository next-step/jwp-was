package servlet;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import http.Parameters;
import java.util.Map;
import model.User;
import view.RedirectView;
import view.View;

public class UserCreateServlet extends AbstractHttpServlet {

  @Override
  public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    User user = createUser(httpRequest.getParameters());
    DataBase.addUser(user);
    return new RedirectView("/index.html");
  }

  User createUser(Map<String, String> parameters) {
    return new User(parameters.get("userId"), parameters.get("password"),
        parameters.get("name"), parameters.get("email"));
  }

}
