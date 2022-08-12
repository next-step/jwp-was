package mvc.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.util.HashMap;
import java.util.Map;

public class ControllerMatcher {

    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new UserCreateController());
        controllers.put("/user/login", new UserLoginController());
        controllers.put("/user/list", new UserListController());
        controllers.put("./static", new StaticController());
        controllers.put("./template", new TemplateController());
    }

    public static Controller matchController(String path) {
        Controller controller = controllers.get(path);
        if (controller != null) {
            return controller;
        }

        if (path.endsWith(".html")) {
            return controllers.get("./template");
        }
        return controllers.get("./static");
    }
}
