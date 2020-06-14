package webserver;

import controller.Controller;
import http.FileExtension;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

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

            FileExtension fileExtension = getExtension(path);
            String filePath = fileExtension.getPhysicalPath().concat(path);
            byte[] body = fileLoadFromPath(filePath);

            if (body.length == 0) {
                httpResponse.response400Header();
                return;
            }

            httpResponse.addHeader("Content-Type", fileExtension.getMimeType() + ";charset=utf-8");
            httpResponse.response200Header(body.length);
            httpResponse.responseBody(body);
            return;
        }

        controller.service(httpRequest, httpResponse);
    }

    public static byte[] fileLoadFromPath(String path) {
        try {
            if (path.endsWith("/")) return new byte[0];
            return FileIoUtils.loadFileFromClasspath(path);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return new byte[0];
    }

    private static FileExtension getExtension(final String filePath) {
        if (filePath.contains("\\.")) return FileExtension.NONE;
        String[] split = filePath.split("\\.");
        String extension = split[split.length - 1];
        return FileExtension.of(extension);
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
