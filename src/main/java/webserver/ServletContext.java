package webserver;

import servlet.*;

import java.util.*;

public class ServletContext {

    private List<HttpServlet> httpServlets = Arrays.asList(new StaticResourceServlet(),
            new TemplateResourceServlet(),
            new UserCreateServlet(),
            new UserListServlet(),
            new UserLoginServlet());

    Optional<HttpServlet> mapping(Request request) {
        return httpServlets.stream()
                .filter(it -> it.isMapping(request))
                .findFirst();
    }
}