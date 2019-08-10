package webserver;

import webserver.controller.Controller;
import webserver.http.HttpSessionManager;
import webserver.resource.HandlebarsResourceLoader;
import webserver.resource.ResourceLoader;
import webserver.resource.StaticResourceLoader;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * for Dependency Injection Provider
 */
public class WebContext {

    public static final String LOGINED_KEY = "logined";

    public static String WELCOME_PAGE = "/index.html";

    public static String ERROR_PAGE = "/error.html";

    public static String SESSION_KEY = "session-key";

    public static Map<String, Controller> CONTROLLERS = ComponentFactory.getComponents();

    public static List<ResourceLoader> RESOURCE_LOADERS =
            asList(new StaticResourceLoader(), new HandlebarsResourceLoader());

    public static HttpSessionManager HTTP_SESSION_MANAGER = new HttpSessionManager();

}
