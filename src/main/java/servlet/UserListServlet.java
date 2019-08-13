package servlet;

import http.HttpRequest;
import http.HttpResponse;
import java.util.Map;
import view.HandleBarsView;
import view.RedirectView;
import view.View;

public class UserListServlet extends AbstractHttpServlet {

  private static final String LOGIN_COOKIE_KEY = "logined";

  @Override
  public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (isLogined(httpRequest.getCookies())) {
      return new HandleBarsView("/user/list");
    }
    return new RedirectView("/user/login.html");
  }

  private boolean isLogined(Map<String, String> cookies) {
    String logined = cookies.get(LOGIN_COOKIE_KEY);
    return logined != null && "true".equals(logined);
  }

}
