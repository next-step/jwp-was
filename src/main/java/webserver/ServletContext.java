package webserver;

import servlet.*;

import java.util.*;

public class ServletContext {

    private static Map<Condition, HttpServlet> httpServletMap = new HashMap<>();

    static {
        httpServletMap.put(UserCreateServlet::isMapping, new UserCreateServlet());
        httpServletMap.put(UserListServlet::isMapping, new UserListServlet());
        httpServletMap.put(UserLoginServlet::isMapping, new UserLoginServlet());
        httpServletMap.put(StaticResourceServlet::isMapping, new StaticResourceServlet());
        httpServletMap.put(TemplateResourceServlet::isMapping, new TemplateResourceServlet());
    }

    HttpServlet mapping(Request request) {
        return httpServletMap.entrySet().stream()
                .filter(it -> it.getKey().isMapping(request))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse((ignore, response) -> response.notFound());
    }
}