package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.provider.ServiceInstanceProvider;
import webserver.servlet.Servlet;

import java.util.Map;

public class HttpServletHandler implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpServletHandler.class);

    private Map<String, Servlet> servletMappings;

    public HttpServletHandler() {
        servletMappings = ServiceInstanceProvider.getDefaultServlets();
    }

    @Override
    public boolean supports(HttpRequest httpRequest) {
        return servletMappings.containsKey(httpRequest.getPath());
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        Servlet servlet = servletMappings.get(httpRequest.getPath());
        servlet.service(httpRequest, httpResponse);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
