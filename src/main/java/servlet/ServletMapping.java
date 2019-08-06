package servlet;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ServletMapping {

  private static Map<String, ServletCreator> servlets;

  static {
    servlets = Arrays.stream(ServletMappingInfo.values())
        .collect(Collectors.toMap(mapping -> mapping.getPath(), mapping -> mapping.getCreator()));
  }

  public static HttpServlet getServlet(String path) {
    if (!servlets.containsKey(path)) {
      return new DefaultServlet();
    }
    return servlets.get(path).create();
  }
}
