package webserver.provider;

import webserver.Ordered;
import webserver.resource.HandlebarsResourceLoader;
import webserver.resource.ResourceLoader;
import webserver.resource.StaticResourceLoader;
import webserver.response.ResponseHandler;
import webserver.response.ServletResponseHandler;
import webserver.response.StaticResponseHandler;
import webserver.servlet.LoginServlet;
import webserver.servlet.RegistrationServlet;
import webserver.servlet.Servlet;
import webserver.servlet.UserListServlet;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableList.of;
import static java.util.Arrays.asList;

/**
 * for Dependency Injection Provider
 */
public class ServiceInstanceProvider {

    public static List<ResponseHandler> getDefaultResponseHandlers() {
        List<ResponseHandler> responseHandlers = asList(new ServletResponseHandler(), new StaticResponseHandler());
        responseHandlers.sort(Comparator.comparingInt(Ordered::getOrder));
        return responseHandlers;
    }

    public static Map<String, Servlet> getDefaultServlets() {
        List<Servlet> servlets = of(new RegistrationServlet(), new LoginServlet(), new UserListServlet());

        Map<String, Servlet> servletMappings = new HashMap<>();
        for (Servlet servlet : servlets) {
            servletMappings.put(servlet.getName(), servlet);
        }
        return servletMappings;
    }

    public static List<ResourceLoader> getDefaultResourceLoaders() {
        return asList(new StaticResourceLoader(), new HandlebarsResourceLoader());
    }

}
