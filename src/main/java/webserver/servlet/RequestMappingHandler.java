package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestMappingHandler {

    public final Map<String, Servlet> servletMap;

    public RequestMappingHandler(List<Servlet> servlets) {
        servletMap = servlets.stream()
                .collect(Collectors.toMap(Servlet::getRequestPath, e -> e));
    }

    public HttpResponse doService(HttpRequest httpRequest) {
        String requestPath = httpRequest.getRequestLine().getPathValue();
        Servlet foundServlet = servletMap.get(requestPath);

        return foundServlet.service(httpRequest);
    }

    public boolean match(HttpRequest httpRequest) {
        String requestPath = httpRequest.getRequestLine().getPathValue();
        return servletMap.containsKey(requestPath);
    }
}
