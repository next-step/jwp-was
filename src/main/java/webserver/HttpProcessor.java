package webserver;

import webserver.request.RequestHolder;
import webserver.response.ResponseHolder;
import webserver.servlet.Servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * web application using servlet
 */
public class HttpProcessor {

    private ResponseHandler responseHandler = new ResponseHandler();
    private Map<String, Servlet> servletMappings = new HashMap<>();

    public HttpProcessor(List<Servlet> servlets) {
        for (Servlet servlet : servlets) {
            servletMappings.put(servlet.getName(), servlet);
        }
    }

    public void process(RequestHolder requestHolder, ResponseHolder responseHolder) {
        if (! servletMappings.containsKey(requestHolder.getPath())) {
            responseHandler.handleStaticResource(responseHolder);
            return;
        }

        Servlet servlet = servletMappings.get(requestHolder.getPath());
        servlet.service(requestHolder, responseHolder);
        responseHandler.handle(responseHolder);
    }
}
