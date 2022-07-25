package webserver.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatus;
import webserver.domain.RequestLine;
import webserver.domain.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FrontController {
    private static final Logger logger = LoggerFactory.getLogger(FrontController.class);
    private final List<Controller> controllers = new ArrayList<>();

    public static FrontController newInstance() {
        return new FrontController();
    }

    public boolean support(RequestLine requestLine) {
        return controllers.stream()
                .anyMatch(controller -> controller.support(requestLine));
    }

    public HttpResponse execute(HttpRequest httpRequest) {
        Controller controller = find(httpRequest.getRequestLine());

        try {
            Method method = controller.getExecutableMethod(httpRequest.getRequestLine());
            HttpResponse httpResponse = (HttpResponse) method.invoke(controller, httpRequest);

            if (method.getDeclaredAnnotation(ResponseBody.class) != null) {
                return httpResponse;
            }

            return httpResponse;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new HttpResponse(HttpStatus.NOT_FOUND, null, e.getMessage());
        }
    }

    private Controller find(RequestLine requestLine) {
        return controllers.stream()
                .filter(controller -> controller.support(requestLine))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public FrontController addController(Controller controller) {
        if (!controllers.contains(controller)) {
            controllers.add(controller);
        }

        return this;
    }
}
