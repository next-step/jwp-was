package webserver.provider;

import webserver.http.Ordered;
import webserver.http.HttpHandler;
import webserver.http.HttpControllerHandler;
import webserver.http.HttpStaticHandler;
import webserver.resource.HandlebarsResourceLoader;
import webserver.resource.ResourceLoader;
import webserver.resource.StaticResourceLoader;
import webserver.servlet.Controller;
import webserver.servlet.ListUserController;
import webserver.servlet.LoginController;
import webserver.servlet.RegistrationController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * for Dependency Injection Provider
 */
public class ServiceInstanceProvider {

    public static <T extends Ordered> List<T> getOrderedList(List<T> source) {
        source.sort(Comparator.comparingInt(Ordered::getOrder));
        return source;
    }

    public static List<HttpHandler> getDefaultResponseHandlers() {
        return getOrderedList(asList(new HttpControllerHandler(), new HttpStaticHandler()));
    }

    public static Map<String, Controller> getDefaultControllers() {
         return new HashMap<String, Controller>(){{
            put("/user/create", new RegistrationController());
            put("/user/login", new LoginController());
            put("/user/list", new ListUserController());

        }};
    }

    public static List<ResourceLoader> getDefaultResourceLoaders() {
        return asList(new StaticResourceLoader(), new HandlebarsResourceLoader());
    }

}
