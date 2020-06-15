package webserver;

import controller.Controller;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestMappingManager {
    private static final Logger logger = LoggerFactory.getLogger(RequestMappingManager.class);

    private final static String ROOT_ABSOLUTE_PATH = "src/main/java/";

    private static final Map<String, Controller> requestMap = new HashMap<>();

    public static void mappingRequestPath() {
        try {
            List<String> controllerNames = getControllerPathFile("controller");

            for (String controllerName : controllerNames) {
                Class controllerClass = Class.forName(controllerName);
                Controller controller = (Controller) controllerClass.newInstance();

                String path = controller.getPath();

                requestMap.put(path, controller);
            }


        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public static Controller getController(String path) {
        return requestMap.get(path);
    }

    public static void execute(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {

        final String path = httpRequest.getPath();
        logger.debug("request url: {}, cookie{}", path, httpRequest.getHeader("Cookie"));

        Controller controller = RequestMappingManager.getController(path);
        if (controller == null) {
            controller = RequestMappingManager.getController("");
        }

        controller.service(httpRequest, httpResponse);
    }



    private static List<String> getControllerPathFile(final String packageName) throws Exception {
        File dir = new File(ROOT_ABSOLUTE_PATH + packageName.replace(".", "/"));

        return Arrays.stream(dir.listFiles())
                .filter(f -> !f.getName().equals("Controller.java") && !f.getName().equals("AbstractController.java"))
                .map(f -> f.getName().replace(".java", ""))
                .map(f -> packageName.concat("." + f))
                .collect(Collectors.toList());
    }
}
