package servlet;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.io.IOException;
import java.util.Map;
import utils.HandleBarsRender;

public class UserListServlet extends AbstractHttpServlet {

  private static final String LOGIN_COOKIE_KEY = "logined";

  @Override
  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    if (isLogined(httpRequest.getCookies())) {

      try {
        String renderView = HandleBarsRender.render("/user/list");
        byte[] view = renderView.getBytes();

        httpResponse.setHttpVersion("HTTP1/1");
        httpResponse.setHttpStatus(HttpStatus.OK);
        httpResponse.setContentType("text/html;charset=utf-8");
        httpResponse.setContentLength(view.length);
        httpResponse.setBody(view);
        httpResponse.render();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return;
    }
    httpResponse.setHttpVersion("HTTP1/1");
    httpResponse.setHttpStatus(HttpStatus.Found);
    httpResponse.setContentType("Content-Type: text/html;charset=utf-8");
    httpResponse.setLocation("/user/login.html");
    httpResponse.render();
  }

  private boolean isLogined(Map<String, String> cookies) {
    String logined = cookies.get(LOGIN_COOKIE_KEY);
    return logined != null && "true".equals(logined);
  }

}
