package servlet;

import http.HttpRequest;
import http.HttpResponse;
import view.View;

abstract class AbstractHttpServlet implements HttpServlet {

  @Override
  public View service(HttpRequest httpRequest, HttpResponse httpResponse) {

    if (httpRequest.isPost()) {
      return doPost(httpRequest, httpResponse);
    }
    if (httpRequest.isGet()) {
      return doGet(httpRequest, httpResponse);
    }
    throw new UnsupportedOperationException("허용되지 않은 메소드 요청입니다.");
  }

  public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    throw new UnsupportedOperationException("허용되지 않은 메소드 요청입니다.");
  }

  public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    throw new UnsupportedOperationException("허용되지 않은 메소드 요청입니다.");
  }

}
