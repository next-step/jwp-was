package servlet;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class ServletMapper {

  private Map<String, HttpServlet> servlets;

  public ServletMapper() {
    this(Collections.EMPTY_MAP);
  }

  public ServletMapper(Map<String, HttpServlet> servletMappingInfo) {
    if (servletMappingInfo.isEmpty()) {
      servlets = getMappingInfo();
      return;
    }
    this.servlets = servletMappingInfo;
  }

  private Map<String, HttpServlet> getMappingInfo() {
    return Arrays.stream(ServletMappingInfo.values())
        .collect(Collectors
            .toMap(mappingInfo -> mappingInfo.getPath(), mappingInfo -> mappingInfo.getServlet()));
  }

  public HttpServlet getServlet(String path) {
    if (!servlets.containsKey(path)) {
      return new DefaultServlet();
    }
    return servlets.get(path);
  }
}
