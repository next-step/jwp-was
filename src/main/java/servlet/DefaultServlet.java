package servlet;

import http.HttpRequest;
import http.HttpResponse;

public class DefaultServlet extends AbstractHttpServlet {

  @Override
  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    httpResponse.forward(httpRequest.getPath());
  }

}
