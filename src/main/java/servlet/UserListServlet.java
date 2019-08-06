package servlet;

import http.HttpRequest;
import http.HttpResponse;

public class UserListServlet extends AbstractHttpServlet {

  @Override
  public void action(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (httpRequest.isLogin()) {
      httpResponse.handleBarView("/user/list");
    }
    httpResponse.sendRedirect("/user/login.html");
  }
}
