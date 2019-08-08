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
    if (httpRequest.isGet()) {
      doGet(httpRequest, httpResponse);
      return;
    }
    throw new UnsupportedOperationException("허용되지 않은 메소드 요청입니다.");
  }

  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    throw new UnsupportedOperationException("허용되지 않은 메소드 요청입니다.");
  }

  ;

  public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    throw new UnsupportedOperationException("허용되지 않은 메소드 요청입니다.");
  }

  ;

}
