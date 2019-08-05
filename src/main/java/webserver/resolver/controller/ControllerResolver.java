package webserver.resolver.controller;

import controller.UserLoginServlet;
import controller.UserSignupServlet;
import webserver.controller.Servlet;
import webserver.request.HttpRequest;
import webserver.resolver.Resolver;
import webserver.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hspark on 2019-08-05.
 */
public class ControllerResolver implements Resolver {
    private static final Map<String, Servlet> CONTROLLERS = new HashMap<>();

    static {
        Servlet userSignupServlet = new UserSignupServlet();
        Servlet userLoginServlet = new UserLoginServlet();
        CONTROLLERS.put(userSignupServlet.getRequestUrl(), userSignupServlet);
        CONTROLLERS.put(userLoginServlet.getRequestUrl(), userLoginServlet);
    }

    @Override
    public void resolve(HttpRequest httpRequest, HttpResponse httpResponse) {
        Servlet servlet = CONTROLLERS.get(httpRequest.getPath());
        servlet.action(httpRequest, httpResponse);
    }
}
