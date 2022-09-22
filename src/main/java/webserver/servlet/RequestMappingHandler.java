package webserver.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestMappingHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestMappingHandler.class);

    public final Map<String, Servlet> servletMap;

    public RequestMappingHandler(List<Servlet> servlets) {
        servletMap = servlets.stream()
                .collect(Collectors.toMap(Servlet::getRequestPath, e -> e));
    }

    public HttpResponse doService(HttpRequest httpRequest) {
        String requestPath = httpRequest.getRequestLine().getPathValue();
        Servlet foundServlet = match(requestPath);

        return foundServlet.service(httpRequest);
    }

    private Servlet match(String requestPath) {
        try {
            return servletMap.get(requestPath);
        } catch (Exception e) {
            logger.error(String.format("servlet not found: %s", requestPath));
            throw new IllegalArgumentException();
        }
    }
}
