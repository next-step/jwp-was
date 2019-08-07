package webserver;

import webserver.resource.HandlebarsResourceLoader;
import webserver.resource.ResourceLoader;
import webserver.resource.StaticResourceLoader;
import webserver.controller.Controller;
import webserver.controller.ListUserController;
import webserver.controller.LoginController;
import webserver.controller.RegistrationController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * for Dependency Injection Provider
 */
public class Context {

    public static final String LOGINED_KEY = "logined";

    public static String WELCOME_PAGE = "/index.html";

    public static String ERROR_PAGE = "/error.html";

    public static Map<String, Controller> CONTROLLERS = new HashMap<String, Controller>(){{
        put("/user/create", new RegistrationController());
        put("/user/login", new LoginController());
        put("/user/list", new ListUserController());
    }};

    public static List<ResourceLoader> RESOURCE_LOADERS =
            asList(new StaticResourceLoader(), new HandlebarsResourceLoader());

}
