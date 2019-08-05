package servlet;

import http.HttpRequest;

public interface HttpServlet {

  String service(HttpRequest httpRequest);

  String doGet(HttpRequest httpRequest);

  String doPost(HttpRequest httpRequest);

}
