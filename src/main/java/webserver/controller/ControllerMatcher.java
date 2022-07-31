package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.util.HashMap;
import java.util.Map;

public class ControllerMatcher {

    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        controllers.put("/user/create", new UserCreateController());
        controllers.put("/user/login", new UserLoginController());
        controllers.put("/user/list", new UserListController(new Handlebars(loader)));
        controllers.put("./static", new StaticController());
        controllers.put("./template", new TemplateController());
    }

    public static Controller matchController(String path) {
        if (path.contains(".html")) {
            return controllers.get("./template");
        }
        if (path.contains(".css")) {
            return controllers.get("./static");
        }
        return controllers.get(path);
    }
}
