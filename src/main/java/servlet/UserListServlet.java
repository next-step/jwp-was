package servlet;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import java.util.Collection;
import java.util.Map;
import model.User;
import view.HandleBarsViewResolver;

public class UserListServlet extends AbstractHttpServlet {

  private static final String LOGIN_COOKIE_KEY = "logined";

  @Override
  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (isLogined(httpRequest.getCookies())) {
      Collection<User> users = DataBase.findAll();
      httpRequest.addAttribute("users", users);
      httpResponse.forward("/user/list", httpRequest, new HandleBarsViewResolver());
      return;
    }
    httpResponse.sendRedirect("/user/login.html");
  }

  private boolean isLogined(Map<String, String> cookies) {
    String logined = cookies.get(LOGIN_COOKIE_KEY);
    return logined != null && "true".equals(logined);
  }

}
