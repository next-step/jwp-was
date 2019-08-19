package servlet;

import http.HttpRequest;
import http.HttpResponse;
import view.StaticViewResolver;
import view.View;

public class StaticResourceServlet extends AbstractHttpServlet {

  @Override
  public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    return new StaticViewResolver().resolve(httpRequest.getPath());
  }

}
