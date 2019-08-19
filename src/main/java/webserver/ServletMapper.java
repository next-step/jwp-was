package webserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import servlet.DefaultServlet;
import servlet.HttpServlet;
import servlet.StaticResourceServlet;

public class ServletMapper {

  private final StaticResourceServlet STATIC_RESOURCE_SERVLET = new StaticResourceServlet();
  private final DefaultServlet DEFAULT_SERVLET = new DefaultServlet();

  private Map<String, HttpServlet> servlets;
  private List<String> staticResourceExtension;

  public ServletMapper() {
    this(Collections.EMPTY_MAP);
  }

  public ServletMapper(Map<String, HttpServlet> servletMappingInfo) {
    staticResourceExtension = Arrays.asList(".css", ".js", ".eot", ".svg", ".woff", ".ttf");
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
    if (isStaticResource(path)) {
      return STATIC_RESOURCE_SERVLET;
    }
    if (!servlets.containsKey(path)) {
      return DEFAULT_SERVLET;
    }
    return servlets.get(path);
  }

  private boolean isStaticResource(String path) {
    if (path == null) {
      return false;
    }
    return staticResourceExtension.stream()
        .anyMatch(extension -> path.contains(extension));
  }
}
