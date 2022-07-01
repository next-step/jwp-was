package was;

import mvc.RequestMapping;
import mvc.controller.Controller;
import mvc.controller.ForwardController;
import mvc.controller.StaticController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class ManualRequestMapping implements RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(ManualRequestMapping.class);

    private static Map<String, Controller> controllers = new HashMap<>();
    private static Controller forwardController = new ForwardController();
    private static Controller staticController = new StaticController();

    static {

    }

    public Controller getController(HttpRequest request) {
        String requestUrl = getDefaultPath(request.getPath());
        logger.debug("Request Mapping Url : {}", requestUrl);

        Controller controller = controllers.get(requestUrl);
        if (controller != null) {
            return controller;
        }

        if (requestUrl.endsWith(".html")) {
            return forwardController;
        }

        return staticController;
    }

    private String getDefaultPath(String path) {
        if (path.equals("/")) {
            return "/index.html";
        }
        return path;
    }
}
