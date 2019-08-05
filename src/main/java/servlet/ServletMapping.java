package servlet;

import http.RequestLine;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ServletMapping {

  private static Map<String, ServletCreator> servlets;

  static {
    servlets = Arrays.stream(ServletMappingInfo.values())
        .collect(Collectors.toMap(mapping -> mapping.getPath(), mapping -> mapping.getCreator()));
  }

  public static HttpServlet getServlet(RequestLine requestLine) {
    if (!servlets.containsKey(requestLine.getPath())) {
      return new DefaultServlet();
    }
    return servlets.get(requestLine.getPath()).create();
  }
}
