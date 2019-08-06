package servlet;

import http.HttpRequest;
import http.HttpResponse;

public class DefaultServlet extends AbstractHttpServlet {

  @Override
  public void action(HttpRequest httpRequest, HttpResponse httpResponse) {
    httpResponse.forward(httpRequest.getPath());
  }
}
