package servlet;

import http.HttpRequest;
import http.HttpResponse;
import view.DefaultViewResolver;
import view.View;

public class DefaultServlet extends AbstractHttpServlet {

  @Override
  public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    return new DefaultViewResolver().resolve(httpRequest.getPath());
  }
}
