package servlet;

import http.RequestLine;

public class DefaultServlet implements HttpServlet {
  @Override
  public String service(RequestLine requestLine) {
    return requestLine.getPath();
  }
}
