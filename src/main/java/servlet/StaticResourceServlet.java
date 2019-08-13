package servlet;

import http.HttpRequest;
import http.HttpResponse;
import view.StaticViewRevolver;
import view.View;

public class StaticResourceServlet extends AbstractHttpServlet {

  @Override
  public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    StaticViewRevolver staticViewRevolver = new StaticViewRevolver();
    return staticViewRevolver.resolve(httpRequest.getPath());
  }

}
