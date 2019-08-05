package servlet;

import http.HttpRequest;

public class DefaultServlet implements HttpServlet {

  @Override
  public String service(HttpRequest httpRequest) {
    return httpRequest.getPath();
  }

  @Override
  public String doGet(HttpRequest httpRequest) {
    return null;
  }

  @Override
  public String doPost(HttpRequest httpRequest) {
    return null;
  }

}
