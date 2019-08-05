package servlet;

import http.RequestLine;

public interface HttpServlet {

  String service(RequestLine requestLine);

}
