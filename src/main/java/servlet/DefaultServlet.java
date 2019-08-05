package servlet;

import http.HttpRequest;
import http.HttpResponse;

public class DefaultServlet implements HttpServlet {

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
    httpResponse.forward(httpRequest.getPath());
  }

  @Override
  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
  }

  @Override
  public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
  }
}
