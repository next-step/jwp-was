package servlet;

import http.HttpRequest;
import http.HttpResponse;
import view.DefaultViewResolver;

public class DefaultServlet extends AbstractHttpServlet {

  @Override
  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    httpResponse.forward(httpRequest.getPath(), httpRequest, new DefaultViewResolver());
  }
}
