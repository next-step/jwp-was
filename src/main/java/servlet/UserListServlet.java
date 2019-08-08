package servlet;

import http.HttpRequest;
import http.HttpResponse;
import java.util.Map;

public class UserListServlet extends AbstractHttpServlet {

  private static final String LOGIN_COOKIE_KEY = "logined";

  @Override
  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (isLogined(httpRequest.getCookies())) {
      httpResponse.handleBarView("/user/list");
    }
    httpResponse.sendRedirect("/user/login.html");
  }

  private boolean isLogined(Map<String, String> cookies) {
    String logined = cookies.get(LOGIN_COOKIE_KEY);
    return logined != null && "true".equals(logined);
  }

}
