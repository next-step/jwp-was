package servlet;

import http.HttpRequest;
import http.HttpResponse;
import view.View;

public interface HttpServlet {

  View service(HttpRequest httpRequest, HttpResponse httpResponse);
  
}
