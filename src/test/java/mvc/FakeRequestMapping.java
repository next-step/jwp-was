package mvc;

import mvc.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class FakeRequestMapping implements RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(FakeRequestMapping.class);

    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/users", new FakeUsersController());
    }

    @Override
    public Controller getController(HttpRequest request) {
        String requestUrl = request.getPath();
        logger.debug("Request Mapping Url : {}", requestUrl);

        Controller controller = controllers.get(requestUrl);
        if (controller != null) {
            return controller;
        }

        return null;
    }

}
