package servlet;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ServletMapper {

  private Map<String, HttpServlet> servlets;

  public ServletMapper() {
    new ServletMapper(getMappingInfo());
  }

  public ServletMapper(Map<String, HttpServlet> servletMappingInfo) {
    servlets = servletMappingInfo;
  }

  private Map<String, HttpServlet> getMappingInfo() {
    return Arrays.stream(ServletMappingInfo.values())
        .collect(Collectors
            .toMap(mappingInfo -> mappingInfo.getPath(), mappingInfo -> mappingInfo.getCreator()));
  }

  public HttpServlet getServlet(String path) {
    if (!servlets.containsKey(path)) {
      return new DefaultServlet();
    }
    return servlets.get(path);
  }
}
