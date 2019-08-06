package servlet;

import http.HttpRequest;
import http.HttpResponse;

public class UserListServlet implements HttpServlet {

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (httpRequest.isPost()) {
      doPost(httpRequest, httpResponse);
      return;
    }
    doGet(httpRequest, httpResponse);
  }

  @Override
  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (httpRequest.isLogin()) {
      httpResponse.handleBarView("/user/list");
    }
    httpResponse.sendRedirect("/user/login.html");
  }

  @Override
  public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

  }
}
