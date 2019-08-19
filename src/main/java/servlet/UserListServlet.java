package servlet;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import java.util.Collection;
import java.util.Collections;
import model.User;
import view.HandleBarsViewResolver;
import view.RedirectView;
import view.View;

public class UserListServlet extends AbstractHttpServlet {

  private static final String LOGIN_SESSION_KEY = "logined";

  @Override
  public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (isLogined(httpRequest.getHttpSession())) {
      Collection<User> users = DataBase.findAll();
      return new HandleBarsViewResolver(Collections.singletonMap("users",users)).resolve("/user/list");
    }
    return new RedirectView("/user/login.html");
  }

  private boolean isLogined(HttpSession session) {
    return (boolean) session.getAttribute(LOGIN_SESSION_KEY);
  }

}
