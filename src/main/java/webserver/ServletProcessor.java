package webserver;

import webserver.request.RequestHolder;
import webserver.servlet.Servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * web application using servlet
 */
public class ServletProcessor {

    private Map<String, Servlet> servletMappings = new HashMap<>();

    public ServletProcessor(List<Servlet> servlets) {
        for (Servlet servlet : servlets) {
            servletMappings.put(servlet.getName(), servlet);
        }
    }

    public void process(RequestHolder requestHolder) {
        if (! servletMappings.containsKey(requestHolder.getPath())) {
            return;
        }

        Servlet servlet = servletMappings.get(requestHolder.getPath());
        servlet.service(requestHolder);
    }
}
