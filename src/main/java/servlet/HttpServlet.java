package servlet;

import http.HttpRequest;
import http.HttpResponse;
import view.View;

public interface HttpServlet {

  void service(HttpRequest httpRequest, HttpResponse httpResponse);
  
}
