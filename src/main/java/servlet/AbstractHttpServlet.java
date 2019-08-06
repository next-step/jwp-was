package servlet;

import http.HttpRequest;
import http.HttpResponse;

abstract class AbstractHttpServlet implements HttpServlet {

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
    action(httpRequest, httpResponse);
  }

  @Override
  public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    action(httpRequest, httpResponse);
  }

  public abstract void action(HttpRequest httpRequest, HttpResponse httpResponse);
}
