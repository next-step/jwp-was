package servlet;

import http.HttpRequest;
import http.HttpResponse;
import view.StaticViewResolver;

public class StaticResourceServlet extends AbstractHttpServlet {

  @Override
  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    httpResponse.forward(httpRequest.getPath(), httpRequest, new StaticViewResolver());
  }

}
